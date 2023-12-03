package br.com.finance.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.finance.dtos.request.ExpenseRequestDto;
import br.com.finance.dtos.response.ExpenseResponseDto;
import br.com.finance.models.Expense;
import br.com.finance.repositories.ExpenseRepository;

@Service
public class ExpenseService {

  @Autowired
  private ExpenseRepository expenseRepository;

  @Autowired
  ModelMapper modelMapper;

  public List<ExpenseResponseDto> getAllExpenses(){
    List<Expense> expenses = expenseRepository.findAll();
    List<ExpenseResponseDto> expenseDto = expenses.stream().map(expense -> modelMapper.map(expense, ExpenseResponseDto.class)).collect(Collectors.toList());
    return expenseDto;
  }

  public ResponseEntity<ExpenseResponseDto> findExpenseById(Long id){
    Optional<Expense> expenseId = expenseRepository.findById(id);
    if(expenseId.isPresent()){
      Expense expense = expenseId.get();
      ExpenseResponseDto expenseDto = modelMapper.map(expense, ExpenseResponseDto.class);
      return ResponseEntity.ok().body(expenseDto);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  public Expense createExpense(ExpenseRequestDto expenseRequestDto){
    Expense expense = modelMapper.map(expenseRequestDto, Expense.class);
    return expenseRepository.save(expense);
  }

  public ResponseEntity<Expense> updateExpenseByid(Long id, ExpenseRequestDto expenseRequestDto){
    Optional<Expense> expenseId = expenseRepository.findById(id);
    if(expenseId.isPresent()){
      Expense expenseToUpdate = expenseId.get();
      modelMapper.map(expenseRequestDto, expenseToUpdate);
      Expense updateExpense = expenseRepository.save(expenseToUpdate);
      return ResponseEntity.ok().body(updateExpense);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  public ResponseEntity<Object> deleteExpenseById(Long id){
    return expenseRepository.findById(id).map(expenseToDelete -> {
      expenseRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    }).orElse(ResponseEntity.notFound().build());
  }
}
