package es.edm.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
  public LoginStatus getLoggedUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && !auth.getName().equals("anonymousUser") && auth.isAuthenticated()) {
    	@SuppressWarnings("unchecked")
		List<GrantedAuthority> authorities = (List<GrantedAuthority>)auth.getAuthorities();
    	return new LoginStatus(true, auth.getName(), authorities);
    } else {
    	return new LoginStatus(false, null, null);
    }
  }

  @ResponseBody
  @RequestMapping(value = "/login.do", method = RequestMethod.POST)
  public LoginStatus login(@RequestBody LoginCredentials credentials) {

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getUserName(), credentials.getUserPwd());

    try {
      Authentication auth = authenticationManager.authenticate(token);
      SecurityContextHolder.getContext().setAuthentication(auth);
      return getLoggedUser();
    } catch (BadCredentialsException e) {
      return new LoginStatus(false, null, null);
    }
  }
  
  @ResponseBody
  @RequestMapping(value = "/logout.do", method = RequestMethod.POST)
  public LoginStatus logout() {
	  SecurityContextHolder.clearContext();
	  return new LoginStatus(false, null, null);
  }
}
