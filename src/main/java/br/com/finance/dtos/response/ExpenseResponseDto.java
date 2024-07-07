package br.com.finance.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.finance.models.Expense;

public record ExpenseResponseDto(Long id, String description, String category, BigDecimal value, LocalDate date, boolean paid_out, Long id_account, Long id_user) {

  public ExpenseResponseDto(Expense expense){
     this(expense.getId(),expense.getDescription(),expense.getCategory(),expense.getValue(),expense.getDate(), expense.isPaid_out(), expense.getAccountId(),expense.getUserId());
  }

}
