package br.com.finance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.finance.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  List<Account> findAccountsByUserId(Long id);
}
