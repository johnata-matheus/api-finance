package br.com.finance.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.finance.domain.revenue.Revenue;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long>{

  @Query(value = "SELECT r FROM Revenue r WHERE r.userId = :id ORDER BY r.date DESC")
  List<Revenue> findAllRevenuesByUser(Long id);

  @Query(value = "SELECT YEAR(r.date), MONTH(r.date), DAY(r.date), SUM(r.value) FROM Revenue r WHERE r.userId = :id AND YEAR(r.date) = :year AND MONTH(r.date) = :month GROUP BY r.date ORDER BY r.date DESC")
  List<Object[]> findAllRevenueValuesFromMonth(Long id, int year, int month);

  @Query(value = "SELECT SUM(r.value) FROM Revenue r WHERE r.userId = :id AND YEAR(r.date) = :year AND MONTH(r.date) = :month")
  Optional<Integer> findTotalRevenuesFromMonth(Long id, int year, int month);
}
