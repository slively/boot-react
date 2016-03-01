package react.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import react.models.UserRole;
import react.persistence.UserRepository;
import react.persistence.models.UserPersistence;
import react.persistence.models.UserRolePersistence;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Profile("default")
@Configuration
public class DefaultConfiguration {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostConstruct
  public void addUserForEachRole() {
    Arrays.asList(UserRole.values()).forEach(userRole -> {
      try {
        log.info("Creating user for role: {}", userRole);
        userRepository.save(
          UserPersistence
            .builder()
            .accountNonExpired(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .enabled(true)
            .password(passwordEncoder.encode(userRole.name()))
            .username(userRole.name())
            .userRoles(Collections.singleton(UserRolePersistence.fromUserRole(userRole)))
            .build()
        );
      } catch (Exception e) {
        log.info("Error creating user for role: {}", userRole);
      }
    });
  }
}
