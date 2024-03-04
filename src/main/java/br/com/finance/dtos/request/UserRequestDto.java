package br.com.finance.dtos.request;

import br.com.finance.enums.Role;
import br.com.finance.models.Revenue;

public record UserRequestDto(String name, String email, String password, Role role, Revenue revenue) {
  
}
