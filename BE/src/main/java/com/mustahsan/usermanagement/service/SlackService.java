package com.mustahsan.usermanagement.service;

import com.mustahsan.usermanagement.dto.SlackMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SlackService {

  @Value("${app.slack.url}")
  private String slackGenericAlertToken;

  @Autowired
  private RestTemplate restTemplate;

  public void sendSlackAlert(SlackMessageRequest request) {
    String url = slackGenericAlertToken;
    restTemplate.postForEntity(url, request, String.class);
  }
}
