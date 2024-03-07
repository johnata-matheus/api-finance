package br.com.finance.dtos.request;

import br.com.finance.enums.Role;
import br.com.finance.models.Revenue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDto(@NotBlank @NotNull String name, @Email String email, @NotBlank @NotNull String password, Role role, Revenue revenue) {
  
}
