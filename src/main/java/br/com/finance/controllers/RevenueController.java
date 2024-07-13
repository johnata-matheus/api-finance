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

import br.com.finance.dtos.request.RevenueRequestDto;
import br.com.finance.dtos.response.PercentageResponseDto;
import br.com.finance.dtos.response.RevenueResponseDto;
import br.com.finance.dtos.response.ValueResponseDto;
import br.com.finance.models.Revenue;
import br.com.finance.models.User;
import br.com.finance.services.RevenueService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/revenue")
public class RevenueController {

  @Autowired
  private RevenueService revenueService;

  @GetMapping("/month")
  public ResponseEntity<List<ValueResponseDto>> getTotalRevenueFromMonth(@RequestParam int year, int month){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long userId = user.getId();
      List<Object[]> revenues = this.revenueService.getValueRevenueMonth(userId, year, month);
      List<ValueResponseDto> responseDto = revenues.stream().map((revenue) -> new ValueResponseDto((int) revenue[0], (int) revenue[1], (int) revenue[2], (BigDecimal) revenue[3])).toList();

      return ResponseEntity.ok().body(responseDto);
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @GetMapping("/percentage")
  public ResponseEntity<PercentageResponseDto> getRevenuePercentage(@RequestParam int yearCurrent, int monthCurrent, int year, int month){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long userId = user.getId();
      int revenue = this.revenueService.getRevenuePercentage(userId, yearCurrent, monthCurrent, year, month);

      return ResponseEntity.ok().body(new PercentageResponseDto(revenue));
    }
     
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @GetMapping
  public ResponseEntity<List<RevenueResponseDto>> getAllRevenues(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long userId = user.getId();
      List<Revenue> revenues = this.revenueService.getAllRevenue(userId);
      List<RevenueResponseDto> revenuesDto = revenues.stream().map(RevenueResponseDto::new).toList();

      return ResponseEntity.ok().body(revenuesDto);
    }
    
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<RevenueResponseDto> getRevenueById(@PathVariable(value = "id") Long id){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long userId = user.getId();
      var revenue = this.revenueService.findRevenueById(id);

      if(revenue != null && revenue.getUserId().equals(userId)){
        return ResponseEntity.ok().body(new RevenueResponseDto(revenue));
      }

      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @PostMapping
  public ResponseEntity<RevenueResponseDto> createRevenue(@RequestBody @Valid RevenueRequestDto revenueRequestDto){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long userId = user.getId();
      var revenue = this.revenueService.createRevenue(userId, revenueRequestDto.toRevenue());

      return ResponseEntity.status(HttpStatus.CREATED).body(new RevenueResponseDto(revenue));
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<RevenueResponseDto> updateRevenue(@PathVariable(value = "id") Long id, @RequestBody @Valid RevenueRequestDto revenueRequestDto){
    var revenue = this.revenueService.updateRevenueByid(id, revenueRequestDto.toRevenue());

    return ResponseEntity.ok().body(new RevenueResponseDto(revenue));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteRevenue(@PathVariable(value = "id") Long id){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication != null && authentication.getPrincipal() instanceof User){
      User user = (User) authentication.getPrincipal();
      Long userId = user.getId();
      var revenue = this.revenueService.findRevenueById(id);

      if(revenue != null && revenue.getUserId().equals(userId)){
        this.revenueService.deleteRevenueById(id);
        return ResponseEntity.noContent().build();
      }

      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
}
