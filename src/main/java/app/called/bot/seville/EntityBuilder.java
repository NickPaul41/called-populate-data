package app.called.bot.seville;

import com.called.data.Address;
import com.called.data.ContentCard;
import com.called.data.Entity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.servantscode.commons.rest.PaginatedResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.called.bot.App.entityClient;

public class EntityBuilder {
    private static final Logger LOG = LogManager.getLogger(EntityBuilder.class);
    private final Map<Integer, Entity> entityMap = new HashMap<>();

    public void createEntities() {
        populateSeville(getFromCSV());
    }

    void populateSeville(List<Entity> entityList){
        int created = 0;
        for (Entity entity: entityList){
            String search = "(name:"+ entity.getName() + ")";
            PaginatedResponse<Entity> searchedEntities = entityClient.getPaginatedResponse(search,0, 100);
            boolean entityFound = false;
            for (Entity e: searchedEntities.getResults()) {
                if(e.getName().equals(entity.getName())) {
                    entityFound = true;
                    entityMap.put(entity.getId(), e);
                    break;
                }
            }
            if(entityFound){
                LOG.info("Entity found! Skipping creation: "+ entity.getName());
            } else {

                LOG.info("Creating entity: " + entity.getName());
                entityMap.put(entity.getId(), entityClient.createEntity(entity));
            }
        }
    }

    List<Entity> getFromCSV() {
        List<Entity> entityList = new ArrayList<>();
        try
        {
            Path path = Paths.get("src/main/resources/entities.csv");
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(bufferedReader);
            for (CSVRecord record : records) {
                Entity entity = getEntity(record);
                entityList.add(entity);
            }

        } catch (IOException e) {
            System.out.println("An error occurred while trying to read the file.");
            LOG.error(e.getMessage());
        }

        return entityList;
    }

    private static Entity getEntity(CSVRecord record) {
        Entity entity = new Entity();
        entity.setId(Integer.parseInt(record.get(0)));
        entity.setName(record.get(1));
        entity.setEntityType(Entity.EntityType.PARISH);
        entity.setPublicEntity(true);
        entity.setMembershipApprovalType(Entity.MembershipApprovalType.OPEN);

        Address address = new Address();
        address.setCity(record.get(3));
        address.setState(record.get(4));
        address.setStreet1(record.get(2));
        address.setPostalCode(record.get(5));

        entity.addAddress(address);
        ContentCard contentCard = new ContentCard();
        contentCard.setContent(record.get(6));
        contentCard.setTitle("Mass Times");
        entity.setContentCards(Collections.singletonList(contentCard));
        return entity;
    }

    public Map<Integer, Entity> getEntityMap() {
        return entityMap;
    }
}
