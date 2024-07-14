package br.com.finance.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.finance.domain.revenue.Revenue;

public record RevenueResponseDto(Long id, String description, BigDecimal value, LocalDate date, Long user_id) {

  public RevenueResponseDto(Revenue revenue){
    this(revenue.getId(), revenue.getDescription(), revenue.getValue(), revenue.getDate(), revenue.getUserId());
  }

}
