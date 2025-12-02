package saeed.sorkh.microservice.core.person;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("${api.base-url}/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/generate-single")
    @Transactional
    public void generatePerson(@RequestBody PersonDTO personDTO) {
        personService.create(personDTO);
    }

    @PostMapping("/generate")
    @Transactional
    public void generateBulkPersons() {
        personService.create(5_000_000, "fn", "ln");
    }

    @PostMapping("/generate/sql")
    public void generateBulkPersonsUsingPureSql() {
        personService.createUsingPureSql(5_000_000, "fn", "ln");
    }

    @DeleteMapping("/{personGroupId}")
    @Transactional
    public void deletePersons(@PathVariable UUID personGroupId) {
        personService.deleteByPersonGroupId(personGroupId);

        throw new IllegalArgumentException("dont commit!");
    }

}
