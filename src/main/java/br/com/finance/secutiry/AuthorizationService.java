package br.com.finance.secutiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.finance.repositories.AuthRepository;

@Service
public class AuthorizationService implements UserDetailsService {

  @Autowired
  private AuthRepository authRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return authRepository.findByEmail(email); 
  }
  
}
