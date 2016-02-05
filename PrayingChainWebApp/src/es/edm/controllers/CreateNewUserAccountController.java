package es.edm.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.edm.controllers.validators.UserAccountValidator;
import es.edm.domain.backingobjects.UserAccount;

@Controller
@RequestMapping(path="/createUserAccount")
public class CreateNewUserAccountController {
	
	@Autowired
	UserAccountValidator validator;
	
	//For creating a new User
	@Autowired
	JdbcUserDetailsManager userManager;
	
	//For automatically log in the new created User
	@Autowired
	@Qualifier("edmAuthenticator")
	AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView showForm(){
		return new ModelAndView("/web/createUserAccount.jsp", "newUser", new UserAccount());
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView processForm(@ModelAttribute("newUser") UserAccount newUser, Errors errors){
		
		validator.validate(newUser, errors);
		
		if (errors.hasErrors()){
			return new ModelAndView("/web/createUserAccount.jsp", "newUser", newUser);
		}
		
		String encodedPassword = encoder.encode(newUser.getPassword());
		
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		roles.add(new SimpleGrantedAuthority("USER"));
		User user = new User(newUser.getUserAccount(), encodedPassword, roles);
		
		//Check if such user already exists into the db
		try{
			userManager.createUser(user);
		} catch (DuplicateKeyException e){
			errors.reject("UserExists");
			newUser.setPassword(null);
			return new ModelAndView("/web/createUserAccount.jsp", "newUser", newUser);
		}
		
		//This will create a credentials for logging in the new user
		Authentication credentials = new UsernamePasswordAuthenticationToken(newUser.getUserAccount(), newUser.getPassword());
		
		//Authenticate creates a new Instance of Authentication interface, so we need to catch them...
		credentials = authenticationManager.authenticate(credentials);
		
		/* We ask if the user was properly authenticated, as it is supossed to be, but from time to time,
		 * maybe for any kind of bug, it fails. 
		 */
		if (credentials.isAuthenticated()){
			
			//Although we could thought that the user is already authenticated, it is not until we call this...
			SecurityContextHolder.getContext().setAuthentication(credentials);
			return new ModelAndView("redirect:/index.html");

		} else {
			throw new RuntimeException("Something went really wrong with such user authentication process!");
		}
	}
}
