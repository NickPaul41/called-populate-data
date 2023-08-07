package app.called.bot.sendbird;

import com.called.data.Affiliation;
import com.called.data.Entity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.text.StringSubstitutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;
import org.servantscode.commons.rest.PaginatedResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static app.called.bot.App.affiliationClient;
import static app.called.bot.App.entityClient;

public class Conversation {
    private static final Logger LOG = LogManager.getLogger(Conversation.class);

    public void bibleStudy(Entity e){
        PaginatedResponse<Affiliation> affiliationList =  affiliationClient.getByEntityId(e.getId());
        if(affiliationList.getResults().size() < 4){
            LOG.error("Entity: " + e.getName() + "does not have enough people for this conversation.");
            return;
        }
        Map<String, String> values = new HashMap<>();
        values.put("person1", affiliationList.getResults().get(0).getPersonName());
        values.put("person2", affiliationList.getResults().get(1).getPersonName());
        values.put("person3", affiliationList.getResults().get(2).getPersonName());
        values.put("person4", affiliationList.getResults().get(3).getPersonName());


        StringSubstitutor sub = new StringSubstitutor(values);
        sub.setEnableSubstitutionInVariables(true);
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        String sendbird_api_id = System.getenv("SENDBIRD_APP_ID");

        defaultClient.setBasePath("https://api-"+ sendbird_api_id + ".sendbird.com");
        Message sendBirdMessage = new Message(defaultClient);

        try
        {
            Path path = Paths.get("src/main/resources/conversation-bible-study.csv");
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(bufferedReader);
            for (CSVRecord record : records) {
                String chatGuid = affiliationList.getResults().get(Integer.parseInt(record.get(0))).getPersonChatGuid();
                String message = sub.replace(record.get(1));
                sendBirdMessage.sendMessage(message, e.getChannelUrl(), chatGuid);
                Thread.sleep(1000);
            }

        } catch (IOException exception) {
            LOG.error("An error occurred while trying to read the file.");

        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void bibleStudy(int id){
        Entity e = entityClient.get(id);
        bibleStudy(e);
    }

}
