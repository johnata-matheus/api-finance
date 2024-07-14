package br.com.finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.finance.domain.balance.Balance;
import br.com.finance.domain.user.User;
import br.com.finance.dtos.response.BalanceResponseDto;
import br.com.finance.services.BalanceService;

@RestController
@RequestMapping("balance")
public class BalanceController {
  
  @Autowired
  private BalanceService balanceService;

  @GetMapping
  public ResponseEntity<BalanceResponseDto> getUserBalance(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
    
    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long userId = user.getId();

      Balance balance = this.balanceService.findBalanceById(userId);
      return ResponseEntity.ok().body(new BalanceResponseDto(balance));
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
}
