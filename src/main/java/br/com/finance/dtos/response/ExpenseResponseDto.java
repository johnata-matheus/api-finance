package br.com.finance.dtos.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseResponseDto {
  private Long id;
  private String description;
  private int value;
  private LocalDate date;
}
