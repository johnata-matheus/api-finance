package br.com.finance.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finance.models.Expense;
import br.com.finance.repositories.ExpenseRepository;

@Service
public class ExpenseService {

  @Autowired
  private ExpenseRepository expenseRepository;

  public List<Expense> getAllExpenses(){
    List<Expense> expenses = this.expenseRepository.findAll();
    
    return expenses;
  }

  public Expense findExpenseById(Long id){
    return this.expenseRepository.findById(id).orElseThrow(() -> new NoSuchElementException("id da despesa não encontrado!"));
  }

  public Expense createExpense(Expense expense){
    return this.expenseRepository.save(expense);
  }

  public Expense updateExpenseByid(Long id, Expense expense){
    Optional<Expense> expenseId = this.expenseRepository.findById(id);
    if(expenseId.isPresent()){
      Expense expenseToUpdate = expenseId.get();

      expenseToUpdate.setDescription(expense.getDescription());
      expenseToUpdate.setCategory(expense.getCategory());
      expenseToUpdate.setValue(expense.getValue());
      expenseToUpdate.setDate(expense.getDate());
      expenseToUpdate.setUser(expense.getUser());

      return this.expenseRepository.save(expenseToUpdate);
    } 
      throw new IllegalArgumentException("Despesa não encontrada com id: " + id);
   
  }

  public void deleteExpenseById(Long id){
      this.expenseRepository.findById(id).ifPresent(expenseToDelete -> {
        this.expenseRepository.deleteById(id);
      });
  }
}
