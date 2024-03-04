package br.com.finance.dtos.request;

import br.com.finance.enums.Role;
import br.com.finance.models.User;

public record RegisterDto(String name, String email, String password, Role role){
  public User toUser(){
    return new User(this.name, this.email, this.password, this.role);
  }
} 
