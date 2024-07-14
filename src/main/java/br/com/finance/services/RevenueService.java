package br.com.finance.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finance.domain.revenue.Revenue;
import br.com.finance.domain.revenue.exceptions.RevenueNotFoundException;
import br.com.finance.repositories.RevenueRepository;

@Service
public class RevenueService {

  @Autowired
  private RevenueRepository revenueRepository;

  @Autowired
  private UserService userService;

  public Integer getTotalRevenueFromMonth(Long id, int year, int month){
    return this.revenueRepository.findTotalRevenuesFromMonth(id, year, month).orElse(0);
  }

  public List<Object[]> getValueRevenueMonth(Long id, int year, int month){
    return this.revenueRepository.findAllRevenueValuesFromMonth(id, year, month);
  }

  public int getRevenuePercentage(Long id, int yearCurrent, int monthCurrent, int year, int month){
    Integer current = this.revenueRepository.findTotalRevenuesFromMonth(id, yearCurrent, monthCurrent).orElse(0);
    Integer previous = this.revenueRepository.findTotalRevenuesFromMonth(id, year, month).orElse(0);

    if(previous != 0){
      double difference = current - previous;
      double division = (difference / previous)* 100 ;
      int result = (int) division;
      
      return result;
    }

    return 0; 
  }

  public List<Revenue> getAllRevenue(Long id){
    this.userService.findUserById(id);

    return this.revenueRepository.findAllRevenuesByUser(id);
  }
  
  public Revenue findRevenueById(Long id){
    return this.revenueRepository.findById(id).orElseThrow(() ->  new RevenueNotFoundException());
  }

  public Revenue createRevenue(Long id, Revenue revenue){
    this.userService.findUserById(id);
    revenue.setUserId(id);

    return this.revenueRepository.save(revenue);
  }
  
  public Revenue updateRevenueByid(Long id, Revenue revenue){
    Revenue revenueToupdate = this.revenueRepository.findById(id).orElseThrow(() -> new RevenueNotFoundException());

    revenueToupdate.setDescription(revenue.getDescription());
    revenueToupdate.setValue(revenue.getValue());
    revenueToupdate.setDate(revenue.getDate());
    
    return this.revenueRepository.save(revenueToupdate);
  }

  public void deleteRevenueById(Long id){
      this.revenueRepository.findById(id).orElseThrow(() -> new RevenueNotFoundException());

      this.revenueRepository.deleteById(id);
  }
  
}
