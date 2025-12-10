package saeed.sorkh.microservice.core.person;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonListener {

    @Value("${message.queue.person}")
    private String queueName;

    private final PersonService personService;

    @RabbitListener(queues = "create-person-queue")
    public void deletePersons(String personGroupId) {
        personService.deleteByPersonGroupId(UUID.fromString(personGroupId));
        System.out.println("Deleting was done");
    }
}
