package br.com.finance.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finance.dtos.request.UserRequestDto;
import br.com.finance.exceptions.UserNotFoundException;
import br.com.finance.models.User;
import br.com.finance.repositories.UserRepository;

@Service
public class UserService {
  
  @Autowired
  private UserRepository userRepository;

  public List<User> getAllUsers(){
    return userRepository.findAll();
  }
  
  public User findUserById(Long id){
    return this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
  }

  public User updateUserByid(Long id, UserRequestDto userRequestDto){
    Optional<User> userId = this.userRepository.findById(id);
    if(userId.isPresent()){
      User userToUpdate = userId.get();
      
      userToUpdate.setName(userRequestDto.name());
      userToUpdate.setEmail(userRequestDto.email());
      userToUpdate.setPassword(userRequestDto.password());
      userToUpdate.setRole(userRequestDto.role());

      return this.userRepository.save(userToUpdate);
    } 

    throw new UserNotFoundException();
  }

  public void deleteUserById(Long id){
    Optional<User> userId = this.userRepository.findById(id);
    if(userId.isPresent()){
      this.userRepository.deleteById(id);
    } else {
      throw new UserNotFoundException();
    }
  }
  
}
