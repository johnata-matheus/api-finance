package br.com.finance.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.finance.exceptions.UserExistsException;
import br.com.finance.exceptions.UserNotFoundException;
import br.com.finance.models.User;
import br.com.finance.repositories.UserRepository;
import br.com.finance.secutiry.TokenService;

@Service
public class AuthService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private EmailService emailService;

  public User registerUser(User user) {
    if (this.userRepository.findByEmail(user.getEmail()) != null) {
      throw new UserExistsException();
    }

    String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
    User newUser = new User(user.getName() ,user.getEmail(), encryptedPassword, user.getRole());

    return this.userRepository.save(newUser);
  }

  public String login(String email, String password){
    try {
      var userNamePassword = new UsernamePasswordAuthenticationToken(email, password);
      var auth = this.authenticationManager.authenticate(userNamePassword); 
   
      return this.tokenService.generateToken((User) auth.getPrincipal());

    } catch (AuthenticationException e) {
        throw new UserNotFoundException();
    }
  }

  public boolean validaToken(String token){
    var serviceOutput = this.tokenService.validateToken(token);
    var isValid = false;

    if(!serviceOutput.isBlank()){
      isValid = true;
    }

    return isValid;
  }

  public User forgotPassword(String email) {
    Optional<User> userEmail = this.userRepository.getEmail(email);

    if(userEmail.isPresent()){
      User user = userEmail.get();
      String token = UUID.randomUUID().toString();  
      LocalDateTime token_expiration = LocalDateTime.now().plusMinutes(60);
      this.emailService.sendEmail(email, token, user);

      user.setToken(token);
      user.setToken_expiration(token_expiration);

      return this.userRepository.save(user);
    } else {
      throw new UserNotFoundException();
    }
    
  }

  public User resetPassword(String token, String password){
    Optional<User> userToken = this.userRepository.findByToken(token);
    
    if(userToken.isPresent()){
      User user = userToken.get();

      if(user.getToken().equals(token) && user.getToken_expiration().isAfter(LocalDateTime.now())){
        String cryptedPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(cryptedPassword);
        user.setToken(null);
        user.setToken_expiration(null);
        
        return this.userRepository.save(user); 
      }

      throw new RuntimeException("Erro na validação do token");
    }
    
    throw new RuntimeException("Token não encontrado");
  }

}