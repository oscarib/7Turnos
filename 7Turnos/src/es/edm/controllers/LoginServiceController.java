package es.edm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.edm.domain.middle.LoginCredentials;
import es.edm.domain.middle.LoginStatus;
import es.edm.services.IOtherServices;

@Controller
public class LoginServiceController {

  @Autowired
  IOtherServices otherServices;

  @ResponseBody
  @RequestMapping(value = "/getLoggedUser.do", method = RequestMethod.POST)
  public LoginStatus getLoggedUser() {
	  return otherServices.getLoggedUser();
  }

  @ResponseBody
  @RequestMapping(value = "/login.do", method = RequestMethod.POST)
  public LoginStatus login(@RequestBody LoginCredentials credentials) {
	  return otherServices.login(credentials);
  }
  
  @ResponseBody
  @RequestMapping(value = "/logout.do", method = RequestMethod.POST)
  public LoginStatus logout() {
	  SecurityContextHolder.clearContext();
	  return new LoginStatus(false, null, -1, null);
  }
}
