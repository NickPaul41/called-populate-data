package app.called.bot.sendbird;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openapitools.client.model.SendBirdMessageResponse;
import org.openapitools.client.model.SendMessageData;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.api.MessageApi;

public class Message {
    private static final Logger LOG = LogManager.getLogger(Message.class);
    private final String apiToken;
    private final MessageApi api;

    public Message(ApiClient defaultClient){
        api = new MessageApi(defaultClient);
        apiToken = System.getenv("SENDBIRD_API_TOKEN");
    }


    public void sendMessage(String message,String channelUrl, String userID){
        SendMessageData sendMessageData = new SendMessageData(); // SendMessageData
        sendMessageData.setMessage(message);
        sendMessageData.setUserId(userID);
        sendMessageData.setChannelUrl(channelUrl);
        sendMessageData.messageType("MESG");
        try {
            SendBirdMessageResponse result = api.sendMessage("group_channels", channelUrl)
                    .apiToken(apiToken)
                    .sendMessageData(sendMessageData)
                    .execute();
            System.out.println(result);
        } catch (ApiException e) {
            LOG.error("Exception when calling MessageApi#sendMessage");
            LOG.error("Status code: " + e.getCode());
            LOG.error("ResponseBody()");
            LOG.error("Response headers: " + e.getResponseHeaders());
            LOG.error(e.getMessage());
        }
    }
}
