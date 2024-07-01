package br.com.finance.dtos.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.finance.models.Expense;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExpenseRequestDto(String description, @NotNull @NotBlank String category, BigDecimal value, LocalDate date, boolean paid_out, Long id_account, Long id_user) {
  public Expense toExpense(){
    return new Expense(this.description, this.category, this.value, this.date, this.paid_out, this.id_account, this.id_user);
  }
}
