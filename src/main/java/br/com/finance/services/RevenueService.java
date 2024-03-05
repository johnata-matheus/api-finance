package br.com.finance.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finance.exceptions.RevenueNotFoundException;
import br.com.finance.models.Revenue;
import br.com.finance.repositories.RevenueRepository;

@Service
public class RevenueService {

  @Autowired
  private RevenueRepository revenueRepository;

  public List<Revenue> getAllRevenue(){
    List<Revenue> revenues = this.revenueRepository.findAll();
    
    return revenues;
  }
  
  public Revenue findRevenueById(Long id){
    return this.revenueRepository.findById(id).orElseThrow(() ->  new RevenueNotFoundException());
  }

  public Revenue createRevenue(Revenue revenue){
    return this.revenueRepository.save(revenue);
  }
  
  public Revenue updateRevenueByid(Long id, Revenue revenue){
    Optional<Revenue> revenueId = this.revenueRepository.findById(id);
    if(revenueId.isPresent()){
      Revenue revenueToUpdate = revenueId.get();

      revenueToUpdate.setDescription(revenue.getDescription());
      revenueToUpdate.setValue(revenue.getValue());
      revenueToUpdate.setDate(revenue.getDate());

      return this.revenueRepository.save(revenueToUpdate);
    }

    throw new RevenueNotFoundException();
  }

  public void deleteRevenueById(Long id){
      Optional<Revenue> revenueId = this.revenueRepository.findById(id);
      if(revenueId.isPresent()){
        this.revenueRepository.deleteById(id);
      } else {
        throw new RevenueNotFoundException();
      }
  }
  
}
