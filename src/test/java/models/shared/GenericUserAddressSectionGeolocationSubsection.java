package models.shared;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class GenericUserAddressSectionGeolocationSubsection {
    String lat;

    @JsonProperty("long")
    String longitude;
}
