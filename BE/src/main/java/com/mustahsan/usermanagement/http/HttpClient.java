package com.mustahsan.usermanagement.http;

import com.mustahsan.usermanagement.dto.GenderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpClient {

  @Autowired
  RestTemplate restTemplate;

  @Value("${gender.api.url}")
  private String genderApi;

  public GenderDTO get(String name){
    String[] split = name.split(" ");
    return restTemplate.getForObject(genderApi+split[0], GenderDTO.class);
  }
}
