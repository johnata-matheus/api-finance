package br.com.finance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.finance.dtos.request.UserRequestDto;
import br.com.finance.dtos.response.UserResponseDto;
import br.com.finance.models.User;
import br.com.finance.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<UserResponseDto> getAllUsers(){
    List<UserResponseDto> users = userService.getAllUsers();
    return users;
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> getUserById(@PathVariable(value = "id") Long id){
    return userService.findUserById(id);
  }

  @PostMapping
  public User createUser(@RequestBody UserRequestDto userRequestDto){
    return userService.createUser(userRequestDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @RequestBody UserRequestDto userRequestDto){
    return userService.updateUserByid(id, userRequestDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long id){
    return userService.deleteUserById(id);
  }
}
