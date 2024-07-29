package models.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    @Data
    public static class SectionName {
        String firstname, lastname;
    }

    @Data
    public static class SectionAddress {
        String city, street;
        Integer number;
        String zipcode;
        SectionGeolocation geolocation;
    }

    @Data
    public static class SectionGeolocation {
        String lat;

        @JsonProperty("long")
        String longitude;
    }
}
