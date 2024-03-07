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
import br.com.finance.dtos.response.UserResponseDto;
import br.com.finance.dtos.response.ValidateReponseDto;

import br.com.finance.services.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<UserResponseDto> register(@RequestBody @Valid RegisterDto registerDto) {
    var user = this.authService.registerUser(registerDto.toUser());

    return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDto(user));
  }

  @PostMapping("/login")
  public ResponseEntity<LoginReponseDto> login(@RequestBody @Valid AuthenticationDto authenticationDto) {
    var token = this.authService.login(authenticationDto.email(), authenticationDto.password());

    return ResponseEntity.ok().body(new LoginReponseDto(token));
  }

  @PostMapping("/validate-token")
  public ResponseEntity<ValidateReponseDto> validate(@RequestBody ValidateRequetDto validateRequetDTO){
    var validateToken = this.authService.validaToken(validateRequetDTO.token());
    
    return ResponseEntity.ok().body(new ValidateReponseDto(validateToken));
  }

}
