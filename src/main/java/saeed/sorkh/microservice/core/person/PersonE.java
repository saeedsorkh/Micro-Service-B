package saeed.sorkh.microservice.core.person;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "person")
@Getter
@Setter
public class PersonE {

    @Id
    @SequenceGenerator(
            name = "person_primary_sequence",
            sequenceName = "person_primary_sequence",
            allocationSize = 10000,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "person_primary_sequence"
    )
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "national_id", nullable = false)
    private long nationalId;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

}
