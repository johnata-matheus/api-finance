package br.com.finance.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.finance.exceptions.ExpenseNotFoundException;
import br.com.finance.exceptions.UserNotFoundException;
import br.com.finance.models.Account;
import br.com.finance.models.Expense;
import br.com.finance.repositories.AccountRepository;
import br.com.finance.repositories.ExpenseRepository;
import br.com.finance.repositories.UserRepository;

@Service
public class ExpenseService {

  @Autowired
  private ExpenseRepository expenseRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private UserRepository userRepository;

  public List<Object[]> getValueExpenseMonth(int year, int month){
    return this.expenseRepository.findTotalExpensesPerMonth(year, month);
  }

  public int getExpensePercentage(@RequestParam int yearCurrent, int monthCurrent, int year, int month){
    Integer current = this.expenseRepository.getTotalExpensesByMonth(yearCurrent, monthCurrent).orElse(0);
    Integer previous = this.expenseRepository.getTotalExpensesByMonth(year, month).orElse(0);

    if(previous != 0){
      double difference = current - previous;
      double division = (difference / previous)* 100 ;
      int result = (int) division;
      
      return result;
    }

    return 0; 
  }
  

  public List<Expense> getExpenseByUser(Long id){
    List<Expense> expenses = this.expenseRepository.findExpensesByUserId(id);

    return expenses;
  }

  public Expense findExpenseById(Long id){
    return this.expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException());
  }

  public Expense createExpense(Expense expense, Long id){
    this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    expense.setUserId(id);

    if(expense.isPaid_out() == true){
      Account account = this.accountRepository.findById(expense.getAccountId()).orElseThrow(() -> new ExpenseNotFoundException());
      
      double accountBalance = account.getBalance();
      double expenseValue = expense.getValue();
      account.setBalance(accountBalance - expenseValue);
      this.accountRepository.save(account);
      
      return this.expenseRepository.save(expense);
    }

    return this.expenseRepository.save(expense);
  }

  public Expense updateExpenseByid(Long id, Expense expense){
    Expense expenseId = this.expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException());

    double ExpenseValue = expenseId.getValue();
    double newExpenseValue = expense.getValue();
    double difference = newExpenseValue - ExpenseValue;

    expenseId.setDescription(expense.getDescription());
    expenseId.setCategory(expense.getCategory());
    expenseId.setValue(expense.getValue());
    expenseId.setDate(expense.getDate());
    expenseId.setUser(expense.getUser());
    expenseId.setPaid_out(expense.isPaid_out());

    if(expense.isPaid_out() == true){
      Account account = this.accountRepository.findById(expense.getAccountId()).orElseThrow(() -> new IllegalArgumentException());
  
      double adjustAccountBalance = account.getBalance() - difference;
      account.setBalance(adjustAccountBalance);

      this.accountRepository.save(account);
    }

    return this.expenseRepository.save(expenseId);
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
