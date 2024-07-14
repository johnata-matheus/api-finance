package br.com.finance.dtos.request;

import java.math.BigDecimal;

import br.com.finance.domain.account.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountRequestDto(@NotNull @NotBlank String type, @NotNull BigDecimal balance, @NotNull @NotBlank String title, Long id_user) {

  public Account toAccount(){
    return new Account(this.type, this.balance, this.title, this.id_user);
  }

}
