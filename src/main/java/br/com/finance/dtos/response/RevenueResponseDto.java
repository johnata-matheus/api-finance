package br.com.finance.dtos.response;

import java.time.LocalDate;

import br.com.finance.models.Revenue;

public record RevenueResponseDto(Long id, String description, int value, LocalDate date, Long user_id) {

  public RevenueResponseDto(Revenue revenue){
    this(revenue.getId(), revenue.getDescription(), revenue.getValue(), revenue.getDate(), revenue.getUser().getId());
  }

}
