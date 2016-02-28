package react.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import react.models.Credentials;
import react.models.User;
import react.persistence.models.UserPersistence;
import react.persistence.UserRepository;
import react.security.UserAuthentication;

@Slf4j
@Component
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

  @Override
  public final User loadUserByUsername(String username) throws UsernameNotFoundException {
    log.debug("Loading user by username {}", username);
    final UserPersistence userPersistence = userRepository.findByUsername(username);
    if (userPersistence == null) {
      throw new UsernameNotFoundException("user not found");
    }
    final User user = userPersistence.toUser();
    log.debug("Checking details for username {}: {}", username, user);
    detailsChecker.check(user);
    log.debug("Successfully loaded username {}: {}", username, user);
    return user;
  }

  @Override
  public User authenticate(Credentials credentials) throws AuthenticationException {
    return this.authenticate(new UserAuthentication(credentials.getUsername(), credentials.getPassword())).getDetails();
  }

  @Override
  public UserAuthentication authenticate(Authentication authentication) throws AuthenticationException {
    UserAuthentication userAuthentication = null;
    if (authentication instanceof UserAuthentication) {
      userAuthentication = (UserAuthentication) authentication;
      User user = loadUserByUsername(userAuthentication.getPrincipal());

      if (passwordEncoder.matches(userAuthentication.getCredentials(), user.getPassword())) {
        userAuthentication = new UserAuthentication(user);
        userAuthentication.setAuthenticated(true);
        userAuthentication.getDetails().eraseCredentials();
      } else {
        throw new BadCredentialsException("Invalid username/password.");
      }
    }

    return userAuthentication;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication == UserAuthentication.class;
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public User register(User user) {
    UserPersistence userPersistence = UserPersistence.fromUser(user);
    userPersistence.setPassword(passwordEncoder.encode(user.getPassword()));
    UserPersistence savedUserPersistence = userRepository.saveAndFlush(userPersistence);
    User registeredUser = savedUserPersistence.toUser();
    registeredUser.eraseCredentials();
    return registeredUser;
  }
}
