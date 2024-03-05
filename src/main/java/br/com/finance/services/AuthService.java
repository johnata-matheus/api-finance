package br.com.finance.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.finance.exceptions.UserExistsException;
import br.com.finance.exceptions.UserNotFoundException;
import br.com.finance.models.User;
import br.com.finance.repositories.AuthRepository;
import br.com.finance.secutiry.TokenService;

@Service
public class AuthService {

  @Autowired
  private AuthRepository authRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  public User registerUser(User user) {
    if (this.authRepository.findByEmail(user.getEmail()) != null) {
      throw new UserExistsException();
    }

    String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
    User newUser = new User(user.getName() ,user.getEmail(), encryptedPassword, user.getRole());

    return this.authRepository.save(newUser);
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

}
