package com.mustahsan.usermanagement.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mustahsan.usermanagement.domain.User;
import com.mustahsan.usermanagement.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessHandlerDev implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User internalUser = userService.getCurrentUser();
        String response = mapper.writeValueAsString(internalUser);
        httpServletResponse.getWriter().println(response);
        httpServletResponse.setContentType("application/json");
    }
}
