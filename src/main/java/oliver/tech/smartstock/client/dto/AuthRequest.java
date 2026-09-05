package oliver.tech.smartstock.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthRequest (@JsonProperty("grant_type")String grantType,
                           @JsonProperty("client_id")String clientId,
                           @JsonProperty("client_id")String clientSecret){
}
