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
    static final String sevilleToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJra1Z5TzlZRWczQ2U4ZVZVNWQ2Q2NJQTJBcGpIVXUzSENJTGhuNDBUVlhzIn0.eyJleHAiOjE2OTE0MzI2ODUsImlhdCI6MTY5MTQzMjA4NSwiYXV0aF90aW1lIjoxNjkxMTYzNzg4LCJqdGkiOiJmODI4OWNjMS1lN2FlLTRkZTQtYmRlNy1mMTkyZTI1YTRhMzUiLCJpc3MiOiJodHRwczovL2Rldi1sb2dpbi5jYWxsZWQuYXBwL3JlYWxtcy9jYWxsZWQiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiOTU0YzU4YWEtOTg2Ni00NDUwLWFhYTYtZjg0N2Q3MDY3NGJiIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiY2FsbGVkLWZsdXR0ZXItYXBwIiwic2Vzc2lvbl9zdGF0ZSI6IjE2YTNhZjFhLTQ4MWYtNDIyMi04ZWY5LTdkYjY0YjU5MzAyMyIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiLyoiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtY2FsbGVkIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBvZmZsaW5lX2FjY2VzcyBjYWxsZWQtYXR0cmlidXRlcyBlbWFpbCIsInNpZCI6IjE2YTNhZjFhLTQ4MWYtNDIyMi04ZWY5LTdkYjY0YjU5MzAyMyIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiTmljayBQYXVsIiwicHJlZmVycmVkX3VzZXJuYW1lIjoibnBhdWxAY2FsbGVkLmFwcCIsImdpdmVuX25hbWUiOiJOaWNrIiwidXNlcklkIjozLCJmYW1pbHlfbmFtZSI6IlBhdWwiLCJlbWFpbCI6Im5wYXVsQGNhbGxlZC5hcHAifQ.fYcUw_oYkJB8VtvcQoQNt5yzELbT-hjALDI9_FHD-WQgJ-3MU0mi-nTRSZm_UnKuJtLCgYUFNMsxcnqvyQ3EqHCI-GmXRhcnjKbCkdARWVYyPwoTYi0KlDBox1GIhwUuhfGnBi0bK5Z1rbcYizEVjFlFBlg8_GX4cYnQ_LCr4Q5aRc7sjoQYTW93SE1hLFC4Lwy7quk-FM-WTShyl1GyDMcRYg2ZWA-HrPexOIMjTr8bO3uI34JnEovPZ-wvSBqxS5mdCQ-XPBTnntlJSj13kRaAXT410oavCzR9z4CuSIZmXnu4Utn5HaKtUoNPeWHeXplFnGx4tcukbHfuuOURZA";
    public static final KeycloakUserClient keycloakUserClient = new KeycloakUserClient();


    public static void main(String[] args) {
        personClient.setToken(sevilleToken);
        entityClient.setToken(sevilleToken);

        PeopleBuilder peopleBuilder = new PeopleBuilder();
        peopleBuilder.createPeople();
        //peopleBuilder.createSpecialPeople();

        EntityBuilder entityBuilder = new EntityBuilder();
        //entityBuilder.createEntities();

        AffiliationBuilder affiliationBuilder = new AffiliationBuilder();
        //affiliationBuilder.createAffiliations(peopleBuilder.getPersonMap(), entityBuilder.getEntityMap());

        Conversation conversation = new Conversation();
        //conversation.bibleStudy(68);
    }
}
