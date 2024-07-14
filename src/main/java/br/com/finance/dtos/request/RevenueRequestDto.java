package br.com.finance.dtos.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.finance.domain.revenue.Revenue;

public record RevenueRequestDto(String description, BigDecimal value, LocalDate date, Long id_user) {
  public Revenue toRevenue(){
    return new Revenue(this.description, this.value, this.date, this.id_user);
  }
}
