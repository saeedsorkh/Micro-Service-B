package saeed.sorkh.microservice.core.person_group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonGroupRepo extends JpaRepository<PersonGroupE, UUID> {
}
