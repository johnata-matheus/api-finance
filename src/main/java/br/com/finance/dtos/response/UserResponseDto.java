package br.com.finance.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.finance.domain.user.Role;
import br.com.finance.domain.user.User;

public record UserResponseDto(Long id, String name, String email, Role role, BigDecimal balance, LocalDateTime createdAt, LocalDateTime updatedAt) {

  public UserResponseDto(User user){
    this(user.getId(), user.getName(), user.getEmail(), user.getRole(),  user.getBalance() != null ? user.getBalance().getBalance() : null, user.getCreatedAt(), user.getUpdatedAt());
  }

}
