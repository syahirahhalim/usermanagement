package com.mustahsan.usermanagement.controller;

import com.mustahsan.usermanagement.domain.User;
import com.mustahsan.usermanagement.service.UserService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/user")
@RestController
@Slf4j
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public List<User> getAllUsers(@RequestParam("role") String role) {
    return userService.findAllUsers();
  }

  @PostMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public User saveUser(@RequestBody User user) {
    return userService.createUser(user);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public void updateUser(@PathVariable Long id, @RequestBody User user) {
    userService.updateUser(id, user);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public void deleteUser(@PathVariable Long id) {
    userService.deleteUserById(id);
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable Long id) {
    return userService.findUserById(id);
  }

  @GetMapping("/current")
  public ResponseEntity<User> getCurrentUser() {
    User current = userService.getCurrentUser();
    if(current != null){
      return ResponseEntity.ok(current);
    }else{
      return ResponseEntity.status(401).build();
    }
  }

}
