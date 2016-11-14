package es.edm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.edm.domain.middle.LoginCredentials;
import es.edm.domain.middle.LoginStatus;

@Controller
public class LoginServiceController {

  @Autowired
  @Qualifier("edmAuthenticator")
  AuthenticationManager authenticationManager;

  @ResponseBody
  @RequestMapping(value = "/getLoggedUser.do", method = RequestMethod.POST)
  public LoginStatus getStatus() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && !auth.getName().equals("anonymousUser") && auth.isAuthenticated()) {
      return new LoginStatus(true, auth.getName());
    } else {
      return new LoginStatus(false, null);
    }
  }

  @ResponseBody
  @RequestMapping(value = "/login.do", method = RequestMethod.POST)
  public LoginStatus login(@RequestBody LoginCredentials credentials) {

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getUserName(), credentials.getUserPwd());

    try {
      Authentication auth = authenticationManager.authenticate(token);
      SecurityContextHolder.getContext().setAuthentication(auth);
      return new LoginStatus(auth.isAuthenticated(), auth.getName());
    } catch (BadCredentialsException e) {
      return new LoginStatus(false, null);
    }
  }
}
