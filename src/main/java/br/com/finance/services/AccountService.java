package br.com.finance.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finance.exceptions.accounts.AccountNotfoundException;
import br.com.finance.models.Account;
import br.com.finance.repositories.AccountRepository;

@Service
public class AccountService {
  
  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private UserService userService;

  public List<Account> getAccountsByUser(Long id){
    this.userService.findUserById(id);

    return this.accountRepository.findAccountsByUserId(id);
  }

  public Account findAccountById(Long id){
    return this.accountRepository.findById(id).orElseThrow(() -> new AccountNotfoundException());
  }

  public Account createAccount(Account account, Long id){
    this.userService.findUserById(id);
    account.setUserId(id);

    return this.accountRepository.save(account);
  }

  public Account updateAccount(Long userId, Long idAccount, Account account){
    Account accountToUpdate = this.accountRepository.findById(idAccount).orElseThrow(() -> new AccountNotfoundException());

    accountToUpdate.setType(account.getType());
    accountToUpdate.setBalance(account.getBalance());
    accountToUpdate.setTitle(account.getTitle());

    return this.accountRepository.save(accountToUpdate);
  }

  public Account subtractPaidExpenseFromAccount(Long idAccount, BigDecimal expenseValue){
    Account accountToUpdate = this.accountRepository.findById(idAccount).orElseThrow(() -> new AccountNotfoundException());
    
    BigDecimal accountBalance = accountToUpdate.getBalance();
    accountToUpdate.setBalance(accountBalance.subtract(expenseValue));

    return this.accountRepository.save(accountToUpdate);
  }

  public Account addPaidExpenseToAccount(Long idAccount, BigDecimal expenseValue) {
    Account accountToUpdate = this.accountRepository.findById(idAccount).orElseThrow(() -> new AccountNotfoundException());

    BigDecimal accountBalance = accountToUpdate.getBalance();
    accountToUpdate.setBalance(accountBalance.add(expenseValue));

    return this.accountRepository.save(accountToUpdate);
  }


  public void deleteAccount(Long id){
    this.accountRepository.findById(id).orElseThrow(() -> new AccountNotfoundException());
    
    this.accountRepository.deleteById(id);
  }
}
