package br.com.finance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.finance.models.User;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {
  UserDetails findByLogin(String login);
}
