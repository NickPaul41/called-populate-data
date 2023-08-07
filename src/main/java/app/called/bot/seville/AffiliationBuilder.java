package app.called.bot.seville;

import com.called.data.Affiliation;
import com.called.data.Entity;
import com.called.data.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static app.called.bot.App.affiliationClient;
import static app.called.bot.App.personClient;

public class AffiliationBuilder {
    private static final Logger LOG = LogManager.getLogger(AffiliationBuilder.class);

    public void createAffiliations(Map<Integer, Person> personMap, Map<Integer, Entity> entityMap){
        populateSeville(getFromCSV(), personMap, entityMap);
    }

    void populateSeville(List<Affiliation> affiliations, Map<Integer, Person> personMap, Map<Integer, Entity> entityMap){
        for (Affiliation affiliation: affiliations){
            createAffiliations(personMap.get(affiliation.getPersonId()), entityMap.get(affiliation.getEntityId()));
        }
    }

    List<Affiliation> getFromCSV() {
        List<Affiliation> affiliationList = new ArrayList<>();
        try
        {
            Path path = Paths.get("src/main/resources/affiliation.csv");
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(bufferedReader);
            for (CSVRecord record : records) {
                Affiliation a = new Affiliation();
                a.setPersonId(Integer.parseInt(record.get(0)));
                a.setEntityId(Integer.parseInt(record.get(1)));
                a.setRoleId(3);
                affiliationList.add(a);
            }

        } catch (IOException e) {
            LOG.error("An error occurred while trying to read the file.");

        }
        return affiliationList;
    }

    void createAffiliations(Person p, Entity e, int roleId){
        if (p == null || e == null)
            return;

        Person person = personClient.getByEmail(p.getPrimaryEmail().getEmail());
        boolean affiliationExists = false;
        for(Affiliation affiliation : person.getAffiliations()){
            if(affiliation.getEntityId() == e.getId()){
                affiliationExists = true;
                break;
            }
        }
        if (!affiliationExists) {
            Affiliation a = new Affiliation();
            a.setStatus(Affiliation.AffiliationStatus.ACTIVE);
            a.setEntityId(e.getId());
            a.setPersonId(p.getId());
            a.setRoleId(roleId);
            affiliationClient.createAffiliation(a);
            LOG.info("Creating Affiliation " + person.getId() + "    " + e.getId());
        } else{
            LOG.info( "Affiliation Exists  " + person.getId() + "    " + e.getId());
        }

    }
    void createAffiliations(Person p, Entity e){
       createAffiliations(p, e, 3);
    }
}
