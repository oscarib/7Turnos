package es.edm.domain.middle;

public class LoginStatus {
    private final boolean loggedIn;
    private final String userName;

    public LoginStatus(boolean loggedIn, String userName) {
      this.loggedIn = loggedIn;
      this.userName = userName;
    }

    public boolean isLoggedIn() {
      return loggedIn;
    }

    public String getUsername() {
      return userName;
    }
}