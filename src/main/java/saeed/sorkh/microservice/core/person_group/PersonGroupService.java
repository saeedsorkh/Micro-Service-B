package saeed.sorkh.microservice.core.person_group;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PersonGroupService {

    private final PersonGroupRepo personGroupRepo;

    public PersonGroupService(PersonGroupRepo personGroupRepo) {
        this.personGroupRepo = personGroupRepo;
    }

    @Transactional
    public PersonGroupE create() {
        return personGroupRepo.save(new PersonGroupE());
    }

    @Transactional
    public void deleteById(UUID id) {
        personGroupRepo.deleteById(id);
    }
}
