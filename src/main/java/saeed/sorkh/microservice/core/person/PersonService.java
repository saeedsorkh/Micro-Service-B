package saeed.sorkh.microservice.core.person;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import saeed.sorkh.microservice.core.person_group.PersonGroupService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepo personRepo;
    private final PersonGroupService personGroupService;

    @Transactional
    public void deleteByPersonGroupId(UUID personGroupId) {
        personRepo.deleteByPersonGroupId(personGroupId);
        personGroupService.deleteById(personGroupId);
    }
}
