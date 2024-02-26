package br.com.finance.dtos.response;

import java.util.List;

import br.com.finance.models.Expense;
import br.com.finance.models.User;

public record UserResponseDto(Long id, String login, List<Expense> expenses) {

  public UserResponseDto(User user){
    this(user.getId(), user.getLogin(), user.getExpenses());
  }

}
