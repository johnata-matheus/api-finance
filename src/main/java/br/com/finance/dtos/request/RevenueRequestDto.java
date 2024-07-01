package br.com.finance.dtos.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.finance.models.Revenue;
import br.com.finance.models.User;

public record RevenueRequestDto(String description, BigDecimal value, LocalDate date, User user) {
  public Revenue toRevenue(){
    return new Revenue(this.description, this.value, this.date, this.user);
  }
}
