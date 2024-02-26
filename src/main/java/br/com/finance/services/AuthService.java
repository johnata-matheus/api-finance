package br.com.finance.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    if (this.authRepository.findByLogin(user.getLogin()) != null) {
      throw new IllegalArgumentException("Usuario já existe");
    }

    String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
    User newUser = new User(user.getLogin(), encryptedPassword, user.getRole());

    return this.authRepository.save(newUser);
  }

  public String login(String login, String password){
    var userNamePassword = new UsernamePasswordAuthenticationToken(login, password);

    var auth = this.authenticationManager.authenticate(userNamePassword);

    return this.tokenService.generateToken((User) auth.getPrincipal());
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
