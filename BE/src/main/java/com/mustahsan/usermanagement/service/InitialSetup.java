package com.mustahsan.usermanagement.service;

import com.mustahsan.usermanagement.domain.User;
import com.mustahsan.usermanagement.domain.UserInfo;
import com.mustahsan.usermanagement.domain.UserRole;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InitialSetup {

  @Autowired
  private UserService userService;

  private static final String ADMIN_USERNAME = "admin";

  @PostConstruct
  public void init(){
    User user = userService.findUserByUsername(ADMIN_USERNAME);

    if(user == null){
      user = new User();
      user.setUsername(ADMIN_USERNAME);
      user.setPassword("Abc@1234");
      user.setRole(UserRole.ADMIN);

      UserInfo info = new UserInfo();
      info.setName("Admin");
      info.setEmail("admin@company.com");
      info.setGender("Male");
      info.setPhoto("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVQYV2NgYAAAAAMAAWgmWQ0AAAAASUVORK5CYII=");

      user.setUserInfo(info);
      userService.createUser(user);
    }
  }


}
