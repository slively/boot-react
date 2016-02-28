package react.models;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
  USER,
  ADMIN;

  public String getAuthority() {
    return this.name();
  }
}
