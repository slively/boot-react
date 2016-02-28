package react.persistence.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import react.models.User;
import react.models.UserRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserPersistence {
  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "username", unique = true, nullable = false)
  private String username;

  @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  private Set<UserRolePersistence> userRoles;

  @Column(name = "account_non_expired")
  private boolean accountNonExpired;

  @Column(name = "account_non_locked")
  private boolean accountNonLocked;

  @Column(name = "credentials_non_expired")
  private boolean credentialsNonExpired;

  @Column(name = "enabled")
  private boolean enabled;

  public User toUser() {
    List<UserRole> roles = new ArrayList<>();

    if (userRoles != null) {
      roles = userRoles.stream().map(UserRolePersistence::getRole).collect(Collectors.toList());
    }

    return new User(
      username,
      password,
      enabled,
      accountNonExpired,
      credentialsNonExpired,
      accountNonLocked,
      roles
    );
  }

  public static UserPersistence fromUser(User user) {
    return UserPersistence
      .builder()
      .accountNonExpired(user.isAccountNonExpired())
      .accountNonLocked(user.isAccountNonLocked())
      .credentialsNonExpired(user.isCredentialsNonExpired())
      .enabled(user.isEnabled())
      .password(user.getPassword())
      .username(user.getUsername())
      .userRoles(user.getRoles().stream().map(UserRolePersistence::fromUserRole).collect(Collectors.toSet()))
      .build();
  }
}
