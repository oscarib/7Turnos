package es.edm.domain.middle;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class LoginStatus {
    private final boolean loggedIn;
    private final String userName;
    private final List<GrantedAuthority> authorities;

    public LoginStatus(boolean loggedIn, String userName, List<GrantedAuthority> authorities) {
      this.loggedIn = loggedIn;
      this.userName = userName;
      this.authorities = authorities;
    }

    public boolean isLoggedIn() {
      return loggedIn;
    }

    public String getUsername() {
      return userName;
    }

	public String getUserName() {
		return userName;
	}

	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}
}