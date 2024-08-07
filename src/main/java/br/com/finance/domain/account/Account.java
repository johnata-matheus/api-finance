package br.com.finance.domain.account;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.finance.domain.expense.Expense;
import br.com.finance.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String type;

  @Column(nullable = false)
  private BigDecimal balance;

  private String title;
  
  @Column(name = "id_user", nullable = false)
  private Long userId;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "id_user", insertable = false, updatable = false)
  private User user;

  @JsonIgnore
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Expense> expense;

  public Account(String type, BigDecimal balance, String title, Long id_user){
    this.type = type;
    this.balance = balance;
    this.title = title;
    this.userId = id_user;
  }
}