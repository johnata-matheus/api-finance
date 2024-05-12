package br.com.finance.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.finance.models.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository <Expense, Long>{
  
  List<Expense> findExpensesByUserId(Long idUsuario);
}
