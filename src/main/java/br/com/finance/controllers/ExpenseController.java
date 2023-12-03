package br.com.finance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.finance.dtos.request.ExpenseRequestDto;
import br.com.finance.dtos.response.ExpenseResponseDto;
import br.com.finance.models.Expense;
import br.com.finance.services.ExpenseService;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

  @Autowired
  private ExpenseService expenseService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ExpenseResponseDto> getAllExpenses(){
    List<ExpenseResponseDto> expenses = expenseService.getAllExpenses();
    return expenses;
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExpenseResponseDto> getExpenseById(@PathVariable(value = "id") Long id){
    return expenseService.findExpenseById(id);
  }

  @PostMapping
  public Expense createExpense(@RequestBody ExpenseRequestDto expenseRequestDto){
    return expenseService.createExpense(expenseRequestDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Expense> updateExpense(@PathVariable(value = "id") Long id, @RequestBody ExpenseRequestDto expenseRequestDto){
    return expenseService.updateExpenseByid(id, expenseRequestDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteExpense(@PathVariable(value = "id") Long id){
    return expenseService.deleteExpenseById(id);
  }
}
