package br.com.finance.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finance.dtos.request.UserRequestDto;
import br.com.finance.models.User;
import br.com.finance.repositories.UserRepository;

@Service
public class UserService {
  
  @Autowired
  private UserRepository userRepository;

  @Autowired
  ModelMapper modelMapper;

  public List<User> getAllUsers(){
    return userRepository.findAll();
  }
  
  public User findUserById(Long id){
    Optional<User> userId = userRepository.findById(id);
    if(userId.isPresent()){
      var user = userId.get();
      return user;
    }
    
    throw new IllegalArgumentException("Usuário não encontrado com o id: " + id);
  }

  public User updateUserByid(Long id, UserRequestDto userRequestDto){
    Optional<User> userId = this.userRepository.findById(id);
    if(userId.isPresent()){
      User userToUpdate = userId.get();
      
      userToUpdate.setLogin(userRequestDto.login());
      userToUpdate.setPassword(userRequestDto.password());
      userToUpdate.setRole(userRequestDto.role());

      return this.userRepository.save(userToUpdate);
    } 

    throw new IllegalArgumentException("Usuário não encontrado com o id: " + id);
  }

  public void deleteUserById(Long id){
    this.userRepository.findById(id).ifPresent((userToDelete) -> {
      this.userRepository.deleteById(id);
    });
  }
  
}
