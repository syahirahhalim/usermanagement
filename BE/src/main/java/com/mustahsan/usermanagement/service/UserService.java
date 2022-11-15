package com.mustahsan.usermanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mustahsan.usermanagement.domain.User;
import com.mustahsan.usermanagement.domain.UserInfo;
import com.mustahsan.usermanagement.dto.CustomUserDetails;
import com.mustahsan.usermanagement.dto.GenderDTO;
import com.mustahsan.usermanagement.dto.SlackMessageRequest;
import com.mustahsan.usermanagement.http.HttpClient;
import com.mustahsan.usermanagement.repository.UserInfoRepository;
import com.mustahsan.usermanagement.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserInfoRepository userInfoRepository;
  private final PasswordEncoder passwordEncoder;
  private final SlackService slackService;
  private final HttpClient httpClient;

  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  public User findUserById(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  public User saveUser(User user) {
    if(user.getId() == null){
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    UserInfo info = user.getUserInfo();
    user.setUserInfo(null);
    User saved = userRepository.save(user);
    info.setUserId(saved.getId());
    info = userInfoRepository.save(info);

    String message = String.format("User %s has been created with role %s", saved.getUsername(), saved.getRole());
    slackService.sendSlackAlert(new SlackMessageRequest(message));
    GenderDTO genderDTO = httpClient.get(info.getName());
    if(!genderDTO.getGender().equalsIgnoreCase(info.getGender())){
      String alert = String.format("Warning: A user %s has been created with possibly wrong gender, expected %s, actual %s", saved.getUsername(), genderDTO.getGender(), info.getGender());
      slackService.sendSlackAlert(new SlackMessageRequest(alert));
    }
    return findUserById(saved.getId());
  }

  public User createUser(User user){
    User existing = findUserByUsername(user.getUsername());
    if(existing != null){
      throw new IllegalStateException("Username already exists");
    }
    return saveUser(user);
  }

  public void deleteUserById(Long id) {
    userRepository.deleteById(id);
  }

  public void updateUser(Long id, User user){
    User existing = findUserById(id);
    if(existing == null){
      throw new IllegalStateException("User does not exist");
    }

    UserInfo existingInfo = existing.getUserInfo();
    UserInfo info = user.getUserInfo();
    existingInfo.setName(info.getName());
    existingInfo.setGender(info.getGender());
    existingInfo.setPhoto(info.getPhoto());
    userInfoRepository.save(existingInfo);
  }

  public User findUserByUsername(String username) {
    return userRepository.findUserByUsername(username);
  }

  public User getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if(auth.isAuthenticated() && auth.getPrincipal() instanceof UserDetails){
      UserDetails user = (UserDetails) auth.getPrincipal();
      User internalUser = this.findUserByUsername(user.getUsername());
      return internalUser;
    }

    return null;
  }
}
