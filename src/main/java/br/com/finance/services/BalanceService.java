package br.com.finance.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finance.domain.balance.Balance;
import br.com.finance.repositories.BalanceRepository;

@Service
public class BalanceService {

  @Autowired
  private BalanceRepository balanceRepository;

  public Balance findBalanceById(Long idUser){
    return this.balanceRepository.findBalanceByUserId(idUser).orElseThrow(() -> new IllegalArgumentException("Saldo relacionado ao usuário não encontrado"));
  }

  public Balance createBalance(Long idUser){
    Balance balance = new Balance();
    balance.setUserId(idUser);
    balance.setBalance(BigDecimal.ZERO);

    return this.balanceRepository.save(balance);
  }

  public Balance subtractPaidExpenseFromBalance(Long idUser, BigDecimal expenseValue){
    Balance balanceToUpdate = this.balanceRepository.findBalanceByUserId(idUser).orElseThrow(() -> new IllegalArgumentException("Saldo nao existe"));

    BigDecimal newBalance = balanceToUpdate.getBalance();
    balanceToUpdate.setBalance(newBalance.subtract(expenseValue));
    
    return this.balanceRepository.save(balanceToUpdate);
  }

  public Balance addPaidExpenseToBalance(Long idUser, BigDecimal expenseValue){
    Balance balanceToUpdate = this.balanceRepository.findBalanceByUserId(idUser).orElseThrow(() -> new IllegalArgumentException("Saldo nao existe"));

    BigDecimal newBalance = balanceToUpdate.getBalance();
    balanceToUpdate.setBalance(newBalance.add(expenseValue));

    return this.balanceRepository.save(balanceToUpdate);
  }
 
}
