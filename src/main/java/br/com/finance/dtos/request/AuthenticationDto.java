package br.com.finance.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthenticationDto(@Email String email, @NotNull @NotBlank String password) {
  
}
