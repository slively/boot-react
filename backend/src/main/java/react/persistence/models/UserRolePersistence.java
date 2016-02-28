package react.persistence.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import react.models.UserRole;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
  name = "user_roles",
  uniqueConstraints = @UniqueConstraint(columnNames = { "role", "user_id" })
)
public class UserRolePersistence {
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name="user_id")
  private UserPersistence user;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private UserRole role;

  public static UserRolePersistence fromUserRole(UserRole userRole) {
    return UserRolePersistence.builder().role(userRole).build();
  }
}
