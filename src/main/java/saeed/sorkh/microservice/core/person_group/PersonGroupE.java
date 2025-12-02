package saeed.sorkh.microservice.core.person_group;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "person_group")
@Getter
@Setter
public class PersonGroupE {

    @Id
    @UuidGenerator
    private UUID id;
}
