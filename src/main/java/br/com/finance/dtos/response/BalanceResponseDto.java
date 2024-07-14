package br.com.finance.dtos.response;

import java.math.BigDecimal;

import br.com.finance.domain.balance.Balance;

public record BalanceResponseDto(BigDecimal balance) {

  public BalanceResponseDto(Balance balance){
    this(balance.getBalance());
  }
}
