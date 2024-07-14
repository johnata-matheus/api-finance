package br.com.finance.dtos.request;

import br.com.finance.domain.user.Role;
import br.com.finance.domain.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDto(@NotBlank @NotNull String name, @Email String email, @NotBlank @NotNull String password, Role role){

  public User toUser(){
    Role userRole = Role.USER;
    return new User(this.name, this.email, this.password, userRole);
  }
} 
