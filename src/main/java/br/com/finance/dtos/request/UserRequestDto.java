package br.com.finance.dtos.request;

import br.com.finance.enums.Role;
import br.com.finance.models.Revenue;

public record UserRequestDto(String login, String password, Role role, Revenue revenue) {
  
}
