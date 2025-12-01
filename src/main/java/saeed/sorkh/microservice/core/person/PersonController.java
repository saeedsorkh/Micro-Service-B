package saeed.sorkh.microservice.core.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("${api.base-url}/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/generate-single")
    public void generatePerson() {
        personService.create(new PersonDTO("name", "last name", -1, new Date()));
    }

    @PostMapping("/generate")
    public void generateBulkPersons() {
        personService.create(5_000_000, "fn", "ln");
    }
}
