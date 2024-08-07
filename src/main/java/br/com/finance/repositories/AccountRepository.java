package br.com.finance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.finance.domain.account.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  
  @Query(value = "SELECT a FROM Account a WHERE userId = :id")
  List<Account> findAccountsByUserId(Long id);
}
