package br.com.finance.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finance.domain.expense.Expense;
import br.com.finance.domain.expense.exceptions.ExpenseNotFoundException;
import br.com.finance.domain.user.User;
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

  public Integer getTotalExpenseFromMonth(Long id, int year, int month){
    return this.expenseRepository.findTotalExpensesFromMonth(id, year, month).orElse(0);
  }

  public List<Object[]> getAllValueExpenseMonth(Long id, int year, int month){
    return this.expenseRepository.findAllExpenseValuesFromMonth(id, year, month);
  }

  public int getExpensePercentage(Long id, int yearCurrent, int monthCurrent, int year, int month){
    Integer current = this.expenseRepository.findTotalExpensesFromMonth(id, yearCurrent, monthCurrent).orElse(0);
    Integer previous = this.expenseRepository.findTotalExpensesFromMonth(id, year, month).orElse(0);

    if(previous != 0){
      double difference = current - previous;
      double division = (difference / previous)* 100 ;
      int result = (int) division;
      
      return result;
    }

    return 0; 
  }
  
  public List<Expense> getExpenseByUser(Long id){
    this.userService.findUserById(id);

    return this.expenseRepository.findAllExpensesByUserId(id);
  }

  public Expense findExpenseById(Long id){
    return this.expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException());
  }

  public Expense createExpense(Expense expense, Long id){
    User user = this.userService.findUserById(id);
    expense.setUserId(id);

    if(expense.isPaid_out() == true){
      if(expense.getAccountId() != null){
        this.accountService.subtractPaidExpenseFromAccount(expense.getAccountId(), expense.getValue());
      }
      this.balanceService.subtractPaidExpenseFromBalance(user.getId(), expense.getValue());
    }

    return this.expenseRepository.save(expense);
  }

  public Expense updateExpenseById(Long idUser, Long idExpense, Expense expense){
    Expense expenseToUpdate = this.expenseRepository.findById(idExpense).orElseThrow(() -> new ExpenseNotFoundException());

    BigDecimal expenseValue = expenseToUpdate.getValue();
    BigDecimal newExpenseValue = expense.getValue();
    BigDecimal difference = newExpenseValue.subtract(expenseValue);

    expenseToUpdate.setDescription(expense.getDescription());
    expenseToUpdate.setCategory(expense.getCategory());
    expenseToUpdate.setValue(expense.getValue());
    expenseToUpdate.setDate(expense.getDate());
    expenseToUpdate.setUser(expense.getUser());

    Long oldAccountId = expenseToUpdate.getAccountId();
    Long newAccountId = expense.getAccountId();

    if (expense.isPaid_out()){
      
      if (oldAccountId != null){

        if(!oldAccountId.equals(newAccountId)){
          this.accountService.addPaidExpenseToAccount(oldAccountId, newExpenseValue);
          this.accountService.subtractPaidExpenseFromAccount(newAccountId, newExpenseValue);
        } 
        else {
          this.accountService.subtractPaidExpenseFromAccount(newAccountId, difference);
        } 
      } else if(newAccountId != null){
        this.accountService.subtractPaidExpenseFromAccount(newAccountId, newExpenseValue);
      }
      if (!expenseToUpdate.isPaid_out()) {
        this.balanceService.subtractPaidExpenseFromBalance(expenseToUpdate.getUserId(), newExpenseValue);
      } else {
        this.balanceService.subtractPaidExpenseFromBalance(expenseToUpdate.getUserId(), difference);
      }
      
    } else {
      if (expenseToUpdate.isPaid_out()) {
          if (oldAccountId != null) {
              this.accountService.addPaidExpenseToAccount(oldAccountId, expenseValue);
          }
          this.balanceService.addPaidExpenseToBalance(expenseToUpdate.getUserId(), expenseValue);
      }
    }
    
    expenseToUpdate.setAccountId(expense.getAccountId());
    expenseToUpdate.setPaid_out(expense.isPaid_out());

    return this.expenseRepository.save(expenseToUpdate);
  }

  public void deleteExpenseById(Long id){
    Expense expenseToDelete = this.expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException());

    if(expenseToDelete.isPaid_out()){
      if(expenseToDelete.getAccountId() != null){
        this.accountService.addPaidExpenseToAccount(expenseToDelete.getAccountId(), expenseToDelete.getValue());
      }
      this.balanceService.addPaidExpenseToBalance(expenseToDelete.getUserId(), expenseToDelete.getValue());
    } 

    this.expenseRepository.deleteById(id);
  }

}
