package app.called.bot.seville;

import app.called.bot.rest.KeycloakSetPassword;
import com.called.data.Email;
import com.called.data.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.keycloak.representations.idm.UserRepresentation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.called.bot.App.keycloakUserClient;
import static app.called.bot.App.personClient;

public class PeopleBuilder {
    private static final Logger LOG = LogManager.getLogger(PeopleBuilder.class);

    private final Map<Integer, Person> personMap = new HashMap<>();

    public void createPeople() {

        populateSeville(getPeopleFromCSV("src/main/resources/people.csv"));
    }

    void populateSeville(List<Person> people){
        for (Person p: people) {
            Person sevillePerson = personClient.getByEmail(p.getPrimaryEmail().getEmail());
            if(sevillePerson == null){
                LOG.info("Creating person: " + p.getPrimaryEmail().getEmail());
                personMap.put(p.getId(), personClient.createPerson(p));
            } else {
                LOG.info("Person Found: " + p.getPrimaryEmail().getEmail());
                personMap.put(p.getId(), sevillePerson);
            }
        }
    }

    List<Person> getPeopleFromCSV(String path){
        List<com.called.data.Person> people = new ArrayList<>();
        Path filePath = Paths.get(path);
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                String[] values = line.split(",");
                if(values.length >= 3){
                    Person person = new Person();
                    person.setId(Integer.parseInt(values[0]));
                    person.setFirstName(values[1]);
                    person.setLastName(values[2]);
                    person.addEmail(new Email(values[3],true));
                    people.add(person);
                }
            }
        } catch (IOException e) {
            LOG.error("An error occurred while trying to read the file.");
            LOG.error(e.getMessage());
        }
        return people;
    }

    public Map<Integer, Person> getPersonMap() {
        return personMap;
    }

    public void createSpecialPeople(){
        List<Person> personList = getPeopleFromCSV("src/main/resources/specialPeople.csv");
        KeycloakSetPassword k = new KeycloakSetPassword();
        for (Person p : personList){
            UserRepresentation ur = keycloakUserClient.getUserByEmail(p.getPrimaryEmail().getEmail());
            if(ur == null){
                ur = new UserRepresentation();
                ur.setEnabled(true);
                ur.setEmailVerified(true);
                ur.setEmail(p.getPrimaryEmail().getEmail());
                ur.setFirstName(p.getFirstName());
                ur.setLastName(p.getLastName());

                keycloakUserClient.addUser(ur);

                ur = keycloakUserClient.getUserByEmail(p.getPrimaryEmail().getEmail());
            }

            k.setPassword(ur, "testing123!");


        }
    }


}
