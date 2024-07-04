package models.getUser;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class GetUserRequestSectionGeolocation {
    String lat;

    @JsonProperty("long")
    String longitude;
}
