package br.com.finance.dtos.response;

import br.com.finance.models.Account;

public record AccountResponseDto(
  Long id,
  String type,
  double balance,
  String title,
  Long id_user
) {

  public AccountResponseDto(Account account){
    this(account.getId(), account.getType(), account.getBalance(), account.getTitle(), account.getId_user());
  }

}
