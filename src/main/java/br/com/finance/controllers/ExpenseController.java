package br.com.finance.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.finance.dtos.request.ExpenseRequestDto;
import br.com.finance.dtos.response.ExpenseResponseDto;
import br.com.finance.dtos.response.PercentageResponseDto;
import br.com.finance.dtos.response.TotalValueResponseDto;
import br.com.finance.models.Expense;
import br.com.finance.models.User;
import br.com.finance.services.ExpenseService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

  @Autowired
  private ExpenseService expenseService;

  @GetMapping("/month")
  public ResponseEntity<List<TotalValueResponseDto>> getTotalExpenses(@RequestParam int year, int month) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long userId = user.getId();
      List<Object[]> expenses = this.expenseService.getValueExpenseMonth(userId, year, month);
      List<TotalValueResponseDto> responseDto = expenses.stream().map(expense -> new TotalValueResponseDto((int) expense[0], (int) expense[1], (int) expense[2], (BigDecimal) expense[3])).toList();

      return ResponseEntity.ok().body(responseDto);
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @GetMapping("/percentage")
  public ResponseEntity<PercentageResponseDto> getExpensePercentage(@RequestParam int yearCurrent, int monthCurrent, int year, int month) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long userId = user.getId();
      int expense = this.expenseService.getExpensePercentage(userId, yearCurrent, monthCurrent, year, month);

      return ResponseEntity.ok().body(new PercentageResponseDto(expense));
    }
     
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @GetMapping
  public ResponseEntity<List<ExpenseResponseDto>> getExpensesByUserAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      
    if (authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long userId = user.getId();
      List<Expense> expenses = this.expenseService.getExpenseByUser(userId);
      List<ExpenseResponseDto> listExpenses = expenses.stream().map(ExpenseResponseDto::new).toList();

      return ResponseEntity.ok().body(listExpenses);
    } 
      
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExpenseResponseDto> getExpenseById(@PathVariable(value = "id") Long id){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long userId = user.getId();
      var expense = this.expenseService.findExpenseById(id);

      if(expense != null && expense.getUserId().equals(userId)){
        return ResponseEntity.ok().body(new ExpenseResponseDto(expense));
      }
      
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
 
  @PostMapping
  public ResponseEntity<ExpenseResponseDto> createExpense(@RequestBody @Valid ExpenseRequestDto expenseRequestDto){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long userId = user.getId();
      var expense = this.expenseService.createExpense(expenseRequestDto.toExpense(), userId);

      return ResponseEntity.status(HttpStatus.CREATED).body(new ExpenseResponseDto(expense));
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<ExpenseResponseDto> updateExpense(@PathVariable(value = "id") Long idExpense, @RequestBody @Valid ExpenseRequestDto expenseRequestDto){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long userId = user.getId();
      var expense = this.expenseService.updateExpenseById(userId, idExpense, expenseRequestDto.toExpense());

      return ResponseEntity.status(HttpStatus.CREATED).body(new ExpenseResponseDto(expense));
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteExpense(@PathVariable(value = "id") Long id){
    this.expenseService.deleteExpenseById(id);

    return ResponseEntity.noContent().build();
  }
}
