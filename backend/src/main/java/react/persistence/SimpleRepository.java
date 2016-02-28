package react.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import react.persistence.models.SimpleModelPersistence;

@Repository
public interface SimpleRepository extends JpaRepository<SimpleModelPersistence, Long> {
}
