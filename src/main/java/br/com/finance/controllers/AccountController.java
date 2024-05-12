package br.com.finance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
  public ResponseEntity<List<AccountResponseDto>> getAccountsByUserAuthenticated(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long id = user.getId();
      List<Account> accounts = this.accountService.getAccountsByUser(id);
      List<AccountResponseDto> accountsDto = accounts.stream().map(AccountResponseDto::new).toList();
      return ResponseEntity.ok().body(accountsDto);
    }
    
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @PostMapping
  public ResponseEntity<AccountResponseDto> createAccount(@RequestBody @Valid AccountRequestDto request){
    var account = this.accountService.createAccount(request.toAccount());

    return ResponseEntity.status(HttpStatus.CREATED).body(new AccountResponseDto(account));
  }
}
