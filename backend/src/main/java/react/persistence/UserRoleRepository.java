package react.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import react.persistence.models.UserRolePersistence;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRolePersistence, Long> {
}
