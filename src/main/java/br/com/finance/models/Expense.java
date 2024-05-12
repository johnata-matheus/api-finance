package br.com.finance.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expense")
public class Expense {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String description;

  @Column(nullable = false)
  private double value;
  
  @Column(nullable = false)
  private String category;

  @Column(nullable = false)
  private LocalDate date;

  @Column(name = "id_user")
  private Long userId;

  @ManyToOne
  @JoinColumn(name = "id_user", insertable = false, updatable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "id_account", insertable = false, updatable = false)
  private Account account;

  public Expense(String description, String category, double value, LocalDate date, Long userId) {
    this.description = description;
    this.category = category;
    this.value = value;
    this.date = date;
    this.userId = userId;
  }
}
