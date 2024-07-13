package br.com.finance.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.finance.dtos.request.AccountRequestDto;
import br.com.finance.dtos.response.AccountResponseDto;
import br.com.finance.models.Account;
import br.com.finance.models.User;
import br.com.finance.services.AccountService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

  @Autowired
  private AccountService accountService;
  
  @GetMapping
  public ResponseEntity<List<AccountResponseDto>> getAccountsByUser(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long userId = user.getId();
      List<Account> accounts = this.accountService.getAccountsByUser(userId);
      List<AccountResponseDto> accountsDto = accounts.stream().map(AccountResponseDto::new).toList();

      return ResponseEntity.ok().body(accountsDto);
    }
    
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable(value = "id") Long id){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long userId = user.getId();
      Account account = this.accountService.findAccountById(id);

      if(account != null && account.getUserId().equals(userId)){
        return ResponseEntity.ok().body(new AccountResponseDto(account));
      }

      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
 
  @PostMapping
  public ResponseEntity<AccountResponseDto> createAccount(@RequestBody @Valid AccountRequestDto request){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long id = user.getId();
      Account account = this.accountService.createAccount(request.toAccount(), id);

      return ResponseEntity.status(HttpStatus.CREATED).body(new AccountResponseDto(account));
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<AccountResponseDto> updateAccount(@PathVariable(value = "id") Long idAccount, @RequestBody AccountRequestDto accountRequestDto){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long userId = user.getId();
      Account accountUpdate = this.accountService.updateAccount(userId, idAccount, accountRequestDto.toAccount());

      if(accountUpdate != null && accountUpdate.getUserId().equals(userId)){
        return ResponseEntity.ok().body(new AccountResponseDto(accountUpdate));
      }

      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }  

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteAccount(@PathVariable(value = "id") Long id){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long userId = user.getId();
      Account account = this.accountService.findAccountById(id);
      
      if(account != null && account.getUserId().equals(userId)){
        this.accountService.deleteAccount(id);
      }

      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    return ResponseEntity.noContent().build();
  }
}
