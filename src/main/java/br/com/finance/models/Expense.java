package br.com.finance.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
  private BigDecimal value;
  
  @Column(nullable = false)
  private String category;

  @Column(nullable = false)
  private LocalDate date;

  @Column(nullable = false)
  private boolean paid_out;

  @Column(name = "id_account")
  private Long accountId;

  @Column(name = "id_user")
  private Long userId;

  @ManyToOne
  @JoinColumn(name = "id_user", insertable = false, updatable = false)
  private User user;

  @ManyToOne(optional = true)
  @JoinColumn(name = "id_account", insertable = false, updatable = false)
  private Account account;

  @CreationTimestamp
  private LocalDateTime created_at;

  public Expense(String description, String category, BigDecimal value, LocalDate date, boolean paid_out, Long accountId, Long userId) {
    this.description = description;
    this.category = category;
    this.value = value;
    this.date = date;
    this.paid_out = paid_out;
    this.accountId = accountId;
    this.userId = userId;
  }
}
