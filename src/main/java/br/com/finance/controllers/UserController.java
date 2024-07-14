package br.com.finance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.finance.domain.user.User;
import br.com.finance.dtos.request.UserRequestDto;
import br.com.finance.dtos.response.UserResponseDto;
import br.com.finance.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userService.getAllUsers();

    return ResponseEntity.ok().body(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> getUserById(@PathVariable(value = "id") Long id) {
    var userId = this.userService.findUserById(id);

    return ResponseEntity.ok().body(new UserResponseDto(userId));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDto> updateUser(@PathVariable(value = "id") Long id, @RequestBody @Valid UserRequestDto userRequestDto) {
    var user = this.userService.updateUserByid(id, userRequestDto);

    return ResponseEntity.ok().body(new UserResponseDto(user));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long id) {
    this.userService.deleteUserById(id);
    
    return ResponseEntity.noContent().build();
  }
}
