package react.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import react.persistence.models.UserPersistence;

@Repository
public interface UserRepository extends JpaRepository<UserPersistence, Long> {
  UserPersistence findByUsername(String username);
}
