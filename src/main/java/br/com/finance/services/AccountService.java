package br.com.finance.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    List<Account> accounts = this.accountRepository.findAccountsByUserId(id);
    
    return accounts;
  }

  public Account findAccountById(Long id){
    return this.accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("conta nao encontrada!"));
  }

  public Account createAccount(Account account, Long id){
    this.userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id do usuario invalido!"));

    account.setId_user(id);

    return this.accountRepository.save(account);
  }

  public Account updateAccount(Long id, Long userId, Account account){
    Account accountUpdate = this.accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Conta nao encontrada!"));

    if(!accountUpdate.getId_user().equals(userId)){
      throw new IllegalArgumentException("Você não tem permissão para editar esta conta");
    }

    accountUpdate.setType(account.getType());
    accountUpdate.setBalance(account.getBalance());
    accountUpdate.setTitle(account.getTitle());

    return this.accountRepository.save(accountUpdate);
  }

  public void deleteAccount(Long id){
    this.accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Conta não encontrada!"));
    
    this.accountRepository.deleteById(id);
  }
}
