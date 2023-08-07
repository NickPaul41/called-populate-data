package app.called.bot.rest;

import com.called.commons.client.KeycloakBaseClient;
import com.called.commons.client.KeycloakUserClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.keycloak.representations.idm.UserRepresentation;
import org.servantscode.commons.EnvProperty;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class KeycloakSetPassword extends KeycloakBaseClient{
    private static final Logger LOG = LogManager.getLogger(KeycloakUserClient.class);

    private static final String REALM = EnvProperty.get("KEYCLOAK_REALM", "called");

    public KeycloakSetPassword() {
        super("/admin/realms/" + REALM + "/users");
    }

    public void setPassword(UserRepresentation user, String password){
        Map<String, Object> body = new HashMap<>();
        body.put("type" , "password");
        body.put("value", password);
        body.put("temporary", false);
        Response resp = put(user.getId()+"/reset-password", body);

        if(resp.getStatus() != 204)
            throw new RuntimeException("Could not set password: Status " + resp.getStatus() + " : " + resp.readEntity(String.class));
    }
}
