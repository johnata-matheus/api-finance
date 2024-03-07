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
import org.springframework.web.bind.annotation.RestController;

import br.com.finance.dtos.request.ExpenseRequestDto;
import br.com.finance.dtos.response.ExpenseResponseDto;
import br.com.finance.models.Expense;
import br.com.finance.services.ExpenseService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

  @Autowired
  private ExpenseService expenseService;
  
  @GetMapping
  public ResponseEntity<List<ExpenseResponseDto>> getAllExpenses(){
    List<Expense> expenses = this.expenseService.getAllExpenses();
    List<ExpenseResponseDto> expensesDto = expenses.stream().map(ExpenseResponseDto::new).toList();
   
    return ResponseEntity.ok().body(expensesDto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExpenseResponseDto> getExpenseById(@PathVariable(value = "id") Long id){
      var expense = this.expenseService.findExpenseById(id);
      var expenseResponseDto = new ExpenseResponseDto(expense);

      return ResponseEntity.ok().body(expenseResponseDto);
  }
 
  @PostMapping
  public ResponseEntity<ExpenseResponseDto> createExpense(@RequestBody @Valid ExpenseRequestDto expenseRequestDto){
    var expense = this.expenseService.createExpense(expenseRequestDto.toExpense());
    
    return ResponseEntity.status(HttpStatus.CREATED).body(new ExpenseResponseDto(expense));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ExpenseResponseDto> updateExpense(@PathVariable(value = "id") Long id, @RequestBody @Valid ExpenseRequestDto expenseRequestDto){
    var expense = this.expenseService.updateExpenseByid(id, expenseRequestDto.toExpense());

    return ResponseEntity.ok().body(new ExpenseResponseDto(expense));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteExpense(@PathVariable(value = "id") Long id){
    this.expenseService.deleteExpenseById(id);

    return ResponseEntity.noContent().build();
  }
}
