package br.com.finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.finance.dtos.request.AuthenticationDto;
import br.com.finance.dtos.request.RegisterDto;
import br.com.finance.dtos.request.ValidateRequetDto;
import br.com.finance.dtos.response.LoginReponseDto;
import br.com.finance.dtos.response.ValidateReponseDto;
import br.com.finance.models.User;

import br.com.finance.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody RegisterDto registerDto) {
    var user = this.authService.registerUser(registerDto.toUser());

    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginReponseDto> login(@RequestBody AuthenticationDto authenticationDto) {
    var token = this.authService.login(authenticationDto.login(), authenticationDto.password());

    return ResponseEntity.ok().body(new LoginReponseDto(token));
  }

  @PostMapping("/validate-token")
  public ResponseEntity<ValidateReponseDto> validate(@RequestBody ValidateRequetDto validateRequetDTO){
    var validateToken = this.authService.validaToken(validateRequetDTO.token());
    
    return ResponseEntity.ok().body(new ValidateReponseDto(validateToken));
  }

}
