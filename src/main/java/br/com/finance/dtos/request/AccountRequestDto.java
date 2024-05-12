package br.com.finance.dtos.request;

import br.com.finance.models.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountRequestDto(
  @NotNull @NotBlank String type,
  @NotNull double balance,
  @NotNull @NotBlank String title,
  @NotNull Long id_user
) {

  public Account toAccount(){
    return new Account(this.type, this.balance, this.title, this.id_user);
  }

}
