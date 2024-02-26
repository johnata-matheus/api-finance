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

import br.com.finance.dtos.request.RevenueRequestDto;
import br.com.finance.dtos.response.RevenueResponseDto;
import br.com.finance.models.Revenue;
import br.com.finance.services.RevenueService;

@RestController
@RequestMapping("/revenue")
public class RevenueController {

  @Autowired
  private RevenueService revenueService;

  @GetMapping
  public ResponseEntity<List<RevenueResponseDto>> getAllRevenues(){
    List<Revenue> revenues = this.revenueService.getAllRevenue();
    List<RevenueResponseDto> revenuesDto = revenues.stream().map(RevenueResponseDto::new).toList();
    
    return ResponseEntity.ok().body(revenuesDto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<RevenueResponseDto> getRevenueById(@PathVariable(value = "id") Long id){
    var revenue = this.revenueService.findRevenueById(id);

    return ResponseEntity.ok().body(new RevenueResponseDto(revenue));
  }

  @PostMapping
  public ResponseEntity<RevenueResponseDto> createRevenue(@RequestBody RevenueRequestDto revenueRequestDto){
    var revenue = this.revenueService.createRevenue(revenueRequestDto.toRevenue());

    return ResponseEntity.status(HttpStatus.CREATED).body(new RevenueResponseDto(revenue));
  }

  @PutMapping("/{id}")
  public ResponseEntity<RevenueResponseDto> updateRevenue(@PathVariable(value = "id") Long id, @RequestBody RevenueRequestDto revenueRequestDto){
    var revenue = this.revenueService.updateRevenueByid(id, revenueRequestDto.toRevenue());

    return ResponseEntity.ok().body(new RevenueResponseDto(revenue));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteRevenue(@PathVariable(value = "id") Long id){
    this.revenueService.deleteRevenueById(id);

    return ResponseEntity.noContent().build();
  }
}
