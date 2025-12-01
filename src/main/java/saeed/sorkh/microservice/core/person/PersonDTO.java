package saeed.sorkh.microservice.core.person;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PersonDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private long nationalId;

    private Date createdAt;

    public PersonDTO(String firstName, String lastName, long nationalId, Date createdAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.createdAt = createdAt;
    }
}
