package br.com.finance.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.finance.dtos.request.UserRequestDto;
import br.com.finance.dtos.response.UserResponseDto;
import br.com.finance.models.User;
import br.com.finance.repositories.UserRepository;

@Service
public class UserService {
  
  @Autowired
  private UserRepository userRepository;

  @Autowired
  ModelMapper modelMapper;

  public List<UserResponseDto> getAllUsers(){
    List<User> users = userRepository.findAll();
    List<UserResponseDto> usersDto = users.stream().map(user -> modelMapper.map(user, UserResponseDto.class)).toList();
    return usersDto;
  }
  
  public ResponseEntity<UserResponseDto> findUserById(Long id){
    Optional<User> userId = userRepository.findById(id);
    if(userId.isPresent()){
      User user = userId.get();
      UserResponseDto userDto = modelMapper.map(user, UserResponseDto.class);
      return ResponseEntity.ok().body(userDto);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  public User createUser(UserRequestDto userRequestDto){
    User user = modelMapper.map(userRequestDto, User.class);
    return userRepository.save(user);
  }

  public ResponseEntity<User> updateUserByid(Long id, UserRequestDto userRequestDto){
    Optional<User> userId = userRepository.findById(id);
    if(userId.isPresent()){
      User userToUpdate = userId.get();
      modelMapper.map(userRequestDto, userToUpdate);
      User updateUser = userRepository.save(userToUpdate);
      return ResponseEntity.ok().body(updateUser);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  public ResponseEntity<Object> deleteUserById(Long id){
    return userRepository.findById(id).map(userToDelete -> {
      userRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    }).orElse(ResponseEntity.notFound().build());
  }
}
