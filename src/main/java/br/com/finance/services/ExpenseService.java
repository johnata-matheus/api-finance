package br.com.finance.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.finance.exceptions.ExpenseNotFoundException;
import br.com.finance.models.Expense;
import br.com.finance.models.User;
import br.com.finance.repositories.ExpenseRepository;

@Service
public class ExpenseService {

  @Autowired
  private ExpenseRepository expenseRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private BalanceService balanceService;

  public List<Object[]> getValueExpenseMonth(int year, int month){
    return this.expenseRepository.findAllExpenseValuesFromMonth(year, month);
  }

  public int getExpensePercentage(@RequestParam int yearCurrent, int monthCurrent, int year, int month){
    Integer current = this.expenseRepository.findTotalExpensesFromMonth(yearCurrent, monthCurrent).orElse(0);
    Integer previous = this.expenseRepository.findTotalExpensesFromMonth(year, month).orElse(0);

    if(previous != 0){
      double difference = current - previous;
      double division = (difference / previous)* 100 ;
      int result = (int) division;
      
      return result;
    }

    return 0; 
  }
  
  public List<Expense> getExpenseByUser(Long id){
    return this.expenseRepository.findExpensesByUserId(id);
  }

  public Expense findExpenseById(Long id){
    return this.expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException());
  }

  public Expense createExpense(Expense expense, Long id){
    User user = this.userService.findUserById(id);
    expense.setUserId(id);

    if(expense.isPaid_out() == true){
      this.accountService.subtractPaidExpenseFromAccountBalance(expense.getAccountId(), expense.getValue());
      this.balanceService.updateBalance(user.getId(), expense.getValue());
    }

    return this.expenseRepository.save(expense);
  }

  public Expense updateExpenseByid(Long idUser, Long idExpense, Expense expense){
    Expense expenseId = this.expenseRepository.findById(idExpense).orElseThrow(() -> new ExpenseNotFoundException());

    BigDecimal expenseValue = expenseId.getValue();
    BigDecimal newExpenseValue = expense.getValue();
    BigDecimal difference = newExpenseValue.subtract(expenseValue);

    expenseId.setDescription(expense.getDescription());
    expenseId.setCategory(expense.getCategory());
    expenseId.setValue(expense.getValue());
    expenseId.setDate(expense.getDate());
    expenseId.setUser(expense.getUser());
    expenseId.setPaid_out(expense.isPaid_out());

    if(expense.isPaid_out() == true){
      this.accountService.subtractPaidExpenseFromAccountBalance(expenseId.getAccountId(), difference);
      this.balanceService.updateBalance(expenseId.getUserId(), difference);
    }

    return this.expenseRepository.save(expenseId);
  }

  public void deleteExpenseById(Long id){
    this.expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException());
    
    this.expenseRepository.deleteById(id);
  }

}
