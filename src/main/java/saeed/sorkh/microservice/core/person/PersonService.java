package saeed.sorkh.microservice.core.person;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PersonService {

    @Value("${db.bath-size}")
    private int batchSize;

    private final EntityManager entityManager;
    private final PersonRepo personRepo;

    public PersonService(EntityManager entityManager, PersonRepo personRepo) {
        this.entityManager = entityManager;
        this.personRepo = personRepo;
    }

    @Transactional
    public PersonE create(PersonDTO personDTO) {
        PersonE personE = new PersonE();
        personE.setFirstName(personDTO.getFirstName());
        personE.setLastName(personDTO.getLastName());
        personE.setNationalId(personDTO.getNationalId());
        personE.setCreatedAt(personDTO.getCreatedAt());

        return personRepo.save(personE);
    }

    @Transactional
    public void create(long count, String namePrefix, String lastNamePrefix) {
        Date nowDate = new Date();
        List<PersonE> personEs = new ArrayList<>(batchSize);

        for (int i = 1; i <= count; i++) {
            PersonE personE = new PersonE();
            personE.setFirstName(namePrefix + "_" + i);
            personE.setLastName(lastNamePrefix + "_" + i);
            personE.setNationalId(i);
            personE.setCreatedAt(nowDate);
            personEs.add(personE);

            if (personEs.size() == batchSize || i == count) {

                for (PersonE p : personEs) entityManager.persist(p);

                entityManager.flush();
                entityManager.clear();
                personEs.clear();
            }
        }
    }
}
