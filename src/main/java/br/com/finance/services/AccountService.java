package br.com.finance.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finance.exceptions.expense.ExpenseNotFoundException;
import br.com.finance.models.Account;
import br.com.finance.repositories.AccountRepository;
import br.com.finance.repositories.UserRepository;

@Service
public class AccountService {
  
  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private UserRepository userRepository;

  public List<Account> getAccountsByUser(Long id){
    return this.accountRepository.findAccountsByUserId(id);
  }

  public Account findAccountById(Long id){
    return this.accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("conta nao encontrada!"));
  }

  public Account createAccount(Account account, Long id){
    this.userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id do usuario invalido!"));
    account.setUserId(id);

    return this.accountRepository.save(account);
  }

  public Account updateAccount(Long userId, Account account){
    Account accountUpdate = this.accountRepository.findById(account.getId()).orElseThrow(() -> new IllegalArgumentException("Conta nao encontrada!"));

    accountUpdate.setType(account.getType());
    accountUpdate.setBalance(account.getBalance());
    accountUpdate.setTitle(account.getTitle());

    return this.accountRepository.save(accountUpdate);
  }

  public Account subtractPaidExpenseFromAccount(Long idAccount, BigDecimal expenseValue){
    Account accountToUpdate = this.accountRepository.findById(idAccount).orElseThrow(() -> new ExpenseNotFoundException());
    
    BigDecimal accountBalance = accountToUpdate.getBalance();
    accountToUpdate.setBalance(accountBalance.subtract(expenseValue));

    return this.accountRepository.save(accountToUpdate);
  }

  public Account addPaidExpenseToAccount(Long idAccount, BigDecimal expenseValue) {
    Account accountToUpdate = this.accountRepository.findById(idAccount).orElseThrow(() -> new IllegalArgumentException());

    BigDecimal accountBalance = accountToUpdate.getBalance();
    accountToUpdate.setBalance(accountBalance.add(expenseValue));

    return this.accountRepository.save(accountToUpdate);
  }


  public void deleteAccount(Long id){
    this.accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Conta não encontrada!"));
    
    this.accountRepository.deleteById(id);
  }
}
