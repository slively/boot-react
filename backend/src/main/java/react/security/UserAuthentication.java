package react.security;

import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import react.models.User;
import react.models.UserRole;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Data
public class UserAuthentication implements Authentication {

  private String name;
  private Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
  private String credentials;
  private User details;
  private String principal;
  private boolean authenticated = true;


  private class UserRoleAuthority implements GrantedAuthority {
    @Getter
    private final String authority;

    public UserRoleAuthority(UserRole userRole) {
      authority = "ROLE_" + userRole.name();
    }
  }

  public UserAuthentication(User user) {
    if (user != null) {
      this.name = user.getUsername();
      this.authorities = user.getRoles().stream().map(userRole -> new UserRoleAuthority(userRole)).collect(Collectors.toList());
      this.credentials = user.getPassword();
      this.details = user;
      this.principal = user.getUsername();
    }
  }

  public UserAuthentication(String username, String password) {
    this.principal = username;
    this.credentials = password;
  }
}
