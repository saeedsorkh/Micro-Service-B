package saeed.sorkh.microservice.core.person_group;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonGroupService {

    private final PersonGroupRepo personGroupRepo;

    @Transactional
    public PersonGroupE create() {
        return personGroupRepo.save(new PersonGroupE());
    }

    @Transactional
    public void deleteById(UUID id) {
        personGroupRepo.deleteById(id);
    }
}
