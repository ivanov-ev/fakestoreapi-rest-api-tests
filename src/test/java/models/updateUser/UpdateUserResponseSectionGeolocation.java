package models.updateUser;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class UpdateUserResponseSectionGeolocation {
    String lat;

    @JsonProperty("long")
    String longitude;
}
