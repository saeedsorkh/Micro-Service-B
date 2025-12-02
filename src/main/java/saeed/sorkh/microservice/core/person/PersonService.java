package saeed.sorkh.microservice.core.person;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import saeed.sorkh.microservice.core.person_group.PersonGroupE;
import saeed.sorkh.microservice.core.person_group.PersonGroupService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PersonService {

    @Value("${db.bath-size}")
    private int batchSize;

    private final DataSource dataSource;
    private final EntityManager entityManager;
    private final PersonRepo personRepo;
    private final PersonGroupService personGroupService;

    public PersonService(DataSource dataSource, EntityManager entityManager, PersonRepo personRepo, PersonGroupService personGroupService) {
        this.dataSource = dataSource;
        this.entityManager = entityManager;
        this.personRepo = personRepo;
        this.personGroupService = personGroupService;
    }

    @Transactional
    public PersonE create(PersonDTO personDTO) {
        PersonE personE = new PersonE();
        personE.setFirstName(personDTO.getFirstName());
        personE.setLastName(personDTO.getLastName());
        personE.setNationalId(personDTO.getNationalId());
        personE.setCreatedAt(new Date());
        personE.setPersonGroup(personGroupService.create());

        return personRepo.save(personE);
    }

    /**
     * Creates bulk of {@link PersonE}'s, all in a session, so this method follows ACID rules,
     * but its performance is lower than {@link #createUsingPureSql}.
     *
     * @see #createUsingPureSql
     */
    @Transactional
    public PersonGroupE create(long count, String namePrefix, String lastNamePrefix) {
        Date nowDate = new Date();
        PersonGroupE personGroupE = personGroupService.create();

        List<PersonE> personEs = new ArrayList<>(batchSize);

        for (int i = 1; i <= count; i++) {
            PersonE personE = new PersonE();
            personE.setFirstName(namePrefix + "_" + i);
            personE.setLastName(lastNamePrefix + "_" + i);
            personE.setNationalId(i);
            personE.setCreatedAt(nowDate);
            personE.setPersonGroup(personGroupE);
            personEs.add(personE);

            if (personEs.size() == batchSize || i == count) {

                for (PersonE p : personEs) entityManager.persist(p);

                entityManager.flush();
                entityManager.clear();
                personEs.clear();
            }
        }

        return personGroupE;
    }

    /**
     * Creates bulk of {@link PersonE}'s, each batch will be commited to DB, no matter any exception raised in the next batchs!,
     * so this method NOT follows ACID rules,
     * but its performance is way better than {@link #create(long, String, String)}, because Hibernate does not track entities, so its faster.
     *
     * @see #create(long, String, String)
     */
    public PersonGroupE createUsingPureSql(int count, String namePrefix, String lastNamePrefix) {
        java.sql.Date nowDate = new java.sql.Date(System.currentTimeMillis());
        PersonGroupE personGroupE = personGroupService.create();

        String insertQuery = """
                    INSERT INTO person (first_name, last_name, national_id, created_at, person_group_id)
                    VALUES (?, ?, ?, ?, ?)
                """;

        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(insertQuery);

            for (int i = 0; i < count; i++) {

                statement.setString(1, namePrefix + "_" + i);
                statement.setString(2, lastNamePrefix + "_" + i);
                statement.setLong(3, i);
                statement.setObject(4, nowDate);
                statement.setObject(5, personGroupE.getId());
                statement.addBatch();

                if ((i != 0 && i % batchSize == 0) || i + 1 == count) {
                    statement.executeBatch();
                    connection.commit();
                    statement.clearBatch();
                    System.out.println("Inserted: " + i);
                }
            }

            System.gc();
        } catch (SQLException ex) {
            System.gc();

//            ex.printStackTrace();
            throw new RuntimeException("use @GeneratedValue(strategy = GenerationType.IDENTITY) in PersonE");
        }

        return personGroupE;
    }

    @Transactional
    public void deleteByPersonGroupId(UUID personGroupId) {
        personRepo.deleteByPersonGroupId(personGroupId);
        personGroupService.deleteById(personGroupId);
    }

}
