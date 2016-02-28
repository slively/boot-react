package react.services;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import react.models.Credentials;
import react.models.User;

public interface UserService extends UserDetailsService, AuthenticationProvider {
  User loadUserByUsername(String username) throws UsernameNotFoundException;
  User authenticate(Credentials credentials) throws AuthenticationException;
  User register(User user);
}
