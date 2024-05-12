package br.com.finance.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finance.exceptions.ExpenseNotFoundException;
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

  public List<Expense> getExpenseByUser(Long id){
    List<Expense> expenses = this.expenseRepository.findExpensesByUserId(id);

    return expenses;
  }

  public Expense findExpenseById(Long id){
    return this.expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException());
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

    throw new ExpenseNotFoundException();
  }

  public void deleteExpenseById(Long id){
      Optional<Expense> expenseId = this.expenseRepository.findById(id);
      if(expenseId.isPresent()){
        this.expenseRepository.deleteById(id);
      } else {
        throw new ExpenseNotFoundException();
      }
  }
  
}
