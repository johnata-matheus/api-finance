package br.com.finance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.finance.models.Revenue;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long>{
}
