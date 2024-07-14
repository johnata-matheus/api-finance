package br.com.finance.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.finance.domain.expense.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository <Expense, Long>{
  
  @Query(value = "SELECT e FROM Expense e WHERE e.userId = :id ORDER BY e.created_at DESC")
  List<Expense> findAllExpensesByUserId(Long id);

  @Query(value = "SELECT YEAR(e.date), MONTH(e.date), DAY(e.date), SUM(e.value) FROM Expense e WHERE e.userId = :id AND YEAR(e.date) = :year AND MONTH(e.date) = :month GROUP BY e.date ORDER BY e.date")
  List<Object[]> findAllExpenseValuesFromMonth(Long id, int year, int month);

  @Query("SELECT SUM(e.value) FROM Expense e WHERE e.userId = :id AND YEAR(e.date) = :year AND MONTH(e.date) = :month")
  Optional<Integer> findTotalExpensesFromMonth(Long id, int year, int month);
   
}
