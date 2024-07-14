package br.com.finance.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.finance.domain.user.User;
import br.com.finance.domain.user.exceptions.UserNotExistsException;
import br.com.finance.domain.user.exceptions.UserNotFoundException;
import br.com.finance.dtos.request.UserRequestDto;
import br.com.finance.repositories.UserRepository;

@Service
public class UserService {
  
  @Autowired
  private UserRepository userRepository;

  public List<User> getAllUsers(){
    return userRepository.findAll();
  }
  
  public User findUserById(Long id){
    return this.userRepository.findById(id).orElseThrow(() -> new UserNotExistsException());
  }

  public User updateUserByid(Long id, UserRequestDto userRequestDto){
    User userToUpdate = this.userRepository.findById(id).orElseThrow(() -> new UserNotExistsException());

    String passwordEncrypted = new BCryptPasswordEncoder().encode(userRequestDto.password());
  
    userToUpdate.setName(userRequestDto.name());
    userToUpdate.setEmail(userRequestDto.email());
    userToUpdate.setPassword(passwordEncrypted);
    userToUpdate.setRole(userRequestDto.role());

    return this.userRepository.save(userToUpdate);
  }

  public void deleteUserById(Long id){
    this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

    this.userRepository.deleteById(id);
  }
  
}
