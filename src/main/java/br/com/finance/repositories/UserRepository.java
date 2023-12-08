package br.com.finance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.finance.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
