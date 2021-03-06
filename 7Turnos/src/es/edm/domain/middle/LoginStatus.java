package es.edm.domain.middle;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class LoginStatus {
    private final boolean loggedIn;
    private final String userName;
    private final int chain;
    private final List<GrantedAuthority> authorities;

    public LoginStatus(boolean loggedIn, String userName, int chain, List<GrantedAuthority> authorities) {
        this.loggedIn = loggedIn;
        this.userName = userName;
        this.chain = chain;
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

    public int getChain() {
        return chain;
    }
}