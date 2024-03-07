package br.com.finance.dtos.request;

import java.time.LocalDate;

import br.com.finance.models.Revenue;
import br.com.finance.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RevenueRequestDto(String description, @NotBlank @NotNull double value, @NotBlank @NotNull LocalDate date, User user) {
  public Revenue toRevenue(){
    return new Revenue(this.description, this.value, this.date, this.user);
  }
}
