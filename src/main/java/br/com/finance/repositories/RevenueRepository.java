package br.com.finance.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.finance.models.Revenue;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long>{

  @Query(value = "SELECT YEAR(r.date), MONTH(r.date), DAY(r.date), SUM(r.value) FROM Revenue r WHERE YEAR(r.date) = :year AND MONTH(r.date) = :month GROUP BY YEAR(r.date), MONTH(r.date), DAY(r.date)")
  List<Object[]> findAllRevenueValuesFromMonth(int year, int month);

  @Query(value = "SELECT SUM(r.value) FROM Revenue r WHERE YEAR(r.date) = :year AND MONTH(r.date) = :month")
  Optional<Integer> findTotalRevenuesFromMonth(int year, int month);
}
