package models.addUser;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class AddUserRequestSectionGeolocation {
    String lat;

    @JsonProperty("long")
    String longitude;
}
