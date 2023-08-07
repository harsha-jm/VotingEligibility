package money.jupiter.controllers;

import money.jupiter.models.Person;
import money.jupiter.services.VotingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VotingController {

    private final VotingService votingService;

    public VotingController(VotingService votingService) {
        this.votingService = votingService;
    }

    @PostMapping("/persons")
    public ResponseEntity<Person> addPerson(@RequestBody Person person){
        return ResponseEntity.ok().body(votingService.addPerson(person));
    }
}
