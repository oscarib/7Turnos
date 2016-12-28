package es.edm.services.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.edm.dao.IOthersDao;
import es.edm.domain.entity.ConfigurationEntity;
import es.edm.domain.entity.StatisticsEntity;
import es.edm.domain.middle.LoginCredentials;
import es.edm.domain.middle.LoginStatus;
import es.edm.services.IOtherServices;

@Service
@Transactional
public class OtherServices implements IOtherServices {

	@Autowired
	IOthersDao dao;
	
	@Autowired
	@Qualifier("edmAuthenticator")
	AuthenticationManager authenticationManager;
	  
	@Override
	public ConfigurationEntity getConfiguration() {
		ConfigurationEntity conf = dao.getConfiguration(getLoggedUser()); 
		return conf;
	}

	@Override
	public LoginStatus getLoggedUser() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null && !auth.getName().equals("anonymousUser") && auth.isAuthenticated()) {
	    	@SuppressWarnings("unchecked")
			List<GrantedAuthority> authorities = (List<GrantedAuthority>)auth.getAuthorities();
	    	UserDetails userDetail = (UserDetails) auth.getPrincipal();
	    	int userChain = dao.getUserChain(userDetail.getUsername());
	    	return new LoginStatus(true, auth.getName(), userChain, authorities);
	    } else {
	    	return new LoginStatus(false, null, -1, null);
	    }
	}

	@Override
	public LoginStatus login(LoginCredentials credentials) {
	    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getUserName(), credentials.getUserPwd());

	    try {
	      Authentication auth = authenticationManager.authenticate(token);
	      SecurityContextHolder.getContext().setAuthentication(auth);
	      return getLoggedUser();
	    } catch (BadCredentialsException e) {
	      return new LoginStatus(false, null, -1, null);
	    }
	}

	@Override
	public StatisticsEntity getStatistics() {
		LoginStatus login = getLoggedUser();
		return dao.getStatistics(login);
	}

	@Override
	public String getChainName(int chainNumber) {
		return dao.getChainName(chainNumber);
	}

	@Override
	public boolean setChainName(ConfigurationEntity conf) {
		return dao.setChainName(conf);
	}
}
