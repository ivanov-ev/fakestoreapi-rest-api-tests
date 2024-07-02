package models.deleteUser;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class DeleteUserRequestSectionGeolocation {
    String lat;

    @JsonProperty("long")
    String longitude;
}
