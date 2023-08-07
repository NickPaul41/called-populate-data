package app.called.bot;

import app.called.bot.sendbird.Conversation;
import app.called.bot.seville.AffiliationBuilder;
import app.called.bot.seville.EntityBuilder;
import app.called.bot.seville.PeopleBuilder;
import com.called.client.AffiliationClient;
import com.called.client.EntityClient;
import com.called.client.PersonClient;
import com.called.commons.client.KeycloakUserClient;

public class App {

    public static final PersonClient personClient = new PersonClient();
    public static final EntityClient entityClient = new EntityClient();
    public static final AffiliationClient affiliationClient = new AffiliationClient();

    // Enter a valid token
    static final String sevilleToken = "";
    public static final KeycloakUserClient keycloakUserClient = new KeycloakUserClient();


    public static void main(String[] args) {
        personClient.setToken(sevilleToken);
        entityClient.setToken(sevilleToken);

        PeopleBuilder peopleBuilder = new PeopleBuilder();
        peopleBuilder.createPeople();
        peopleBuilder.createSpecialPeople();

        EntityBuilder entityBuilder = new EntityBuilder();
        entityBuilder.createEntities();

        AffiliationBuilder affiliationBuilder = new AffiliationBuilder();
        affiliationBuilder.createAffiliations(peopleBuilder.getPersonMap(), entityBuilder.getEntityMap());

        Conversation conversation = new Conversation();
        conversation.bibleStudy(68);
    }
}
