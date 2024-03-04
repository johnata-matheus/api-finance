package br.com.finance.dtos.response;

import java.util.List;

import br.com.finance.models.Expense;
import br.com.finance.models.User;

public record UserResponseDto(Long id, String name, String email, List<Expense> expenses) {

  public UserResponseDto(User user){
    this(user.getId(), user.getName(), user.getEmail(), user.getExpenses());
  }

}
