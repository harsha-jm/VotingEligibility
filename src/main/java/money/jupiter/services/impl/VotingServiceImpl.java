package money.jupiter.services.impl;

import money.jupiter.models.Person;
import money.jupiter.repository.PersonRepository;
import money.jupiter.services.VotingService;
//import org.
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;
import org.springframework.stereotype.Service;

import java.io.FileReader;

@Service
public class VotingServiceImpl implements VotingService {

    private final PersonRepository personRepository;

    public VotingServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person addPerson(Person person) {
        Person savedPerson = personRepository.save(person);
        Facts facts = new Facts();
        facts.put("person",savedPerson);
        String result = "false";
        facts.put("result",result);
        MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());
        String fileName = "/Users/harshavardhan/Downloads/VoteEligibility/src/main/resources/rules/application.yml";
        Rule votingRule;
        try {
             votingRule = ruleFactory.createRule(new FileReader(fileName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Rules rules = new Rules();
        rules.register(votingRule);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        System.out.println("Hi can I vote..?");
        rulesEngine.fire(rules,facts);
        System.out.println(result);
        return savedPerson;
    }
}
