package br.com.finance.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.finance.models.Balance;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
  
  Optional<Balance> findBalanceByUserId(Long id);
}