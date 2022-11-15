package com.mustahsan.usermanagement.service;

import com.mustahsan.usermanagement.domain.User;
import com.mustahsan.usermanagement.dto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.findUserByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("User with %s doesn't exist in our database!", username));
    }
    return new CustomUserDetails(user);
  }
}
