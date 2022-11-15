package com.mustahsan.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.context.Theme;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

/*  @RequestMapping(value={"/"}, method = RequestMethod.GET)
  public ModelAndView home() throws IOException {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("index");
    return modelAndView;
  }*/

  @RequestMapping(method = RequestMethod.GET, value = "/page/**")
  public String handleAngularAppUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "forward:/";
  }

}
