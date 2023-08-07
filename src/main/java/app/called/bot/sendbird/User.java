package app.called.bot.sendbird;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openapitools.client.model.ListUsersResponse;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.api.UserApi;

public class User {
    private static final Logger LOG = LogManager.getLogger(User.class);
    String apiToken;
    UserApi apiInstance;
    public User(ApiClient defaultClient){
        apiInstance = new UserApi(defaultClient);
        apiToken = System.getenv("SENDBIRD_API_TOKEN");
    }
    public void listUsers(){

        try {
            Integer limit = 56;
            String activeMode = "activated";
            Boolean showBot = true;
            ListUsersResponse result = apiInstance.listUsers().limit(limit).activeMode(activeMode).apiToken(apiToken).execute();
            System.out.println(result);

        } catch (ApiException e) {
            LOG.error("Exception when calling listUsers");
            LOG.error("Status code: " + e.getCode());
            LOG.error("Reason: " + e.getResponseBody());
            LOG.error("Response headers: " + e.getResponseHeaders());
            LOG.error(e.getMessage());
        }
    }
}