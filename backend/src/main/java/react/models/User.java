package react.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class User extends org.springframework.security.core.userdetails.User {

  @JsonIgnore
  private String password;

  @JsonIgnore
  private Set<GrantedAuthority> authorities;

  public Set<UserRole> getRoles() {

    Collection<GrantedAuthority> authorities = this.getAuthorities();

    if (authorities != null) {
      return authorities
        .stream()
        .map(authority -> UserRole.valueOf(authority.getAuthority()))
        .collect(Collectors.toSet());
    }

    return null;
  }

  public User(String username,
              String password,
              boolean enabled,
              boolean accountNonExpired,
              boolean credentialsNonExpired,
              boolean accountNonLocked,
              Collection<UserRole> userRoles) {

    super(username, password, enabled, accountNonExpired,
      credentialsNonExpired, accountNonLocked, userRoles);
  }
}
