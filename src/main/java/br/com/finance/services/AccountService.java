package br.com.finance.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finance.models.Account;
import br.com.finance.repositories.AccountRepository;

@Service
public class AccountService {
  
  @Autowired
  private AccountRepository accountRepository;

  public List<Account> getAccountsByUser(Long id){
    List<Account> accounts = this.accountRepository.findAccountsByUserId(id);
    
    return accounts;
  }

  public Account createAccount(Account account){
    return this.accountRepository.save(account);
  }

  

}
