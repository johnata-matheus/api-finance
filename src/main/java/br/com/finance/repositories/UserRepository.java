package br.com.finance.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.finance.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  UserDetails findByEmail(String email);
  
  @Query(value = "select u from User u where u.email = :email")
  Optional<User> getEmail(String email);

  Optional<User> findByToken(String token);
}
