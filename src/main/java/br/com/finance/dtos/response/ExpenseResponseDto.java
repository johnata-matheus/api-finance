package br.com.finance.dtos.response;

import java.time.LocalDate;

import br.com.finance.models.Expense;

public record ExpenseResponseDto(Long id,String description,String category,double value,LocalDate date, Long id_user) {

  public ExpenseResponseDto(Expense expense){
     this(expense.getId(),expense.getDescription(),expense.getCategory(),expense.getValue(),expense.getDate(),expense.getUserId());
  } 

}
