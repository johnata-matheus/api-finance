package br.com.finance.dtos.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseRequestDto {
  private String description;
  private int value;
  private LocalDate date;
}
