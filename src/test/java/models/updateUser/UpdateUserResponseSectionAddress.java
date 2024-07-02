package models.updateUser;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class UpdateUserResponseSectionAddress {
    String city, street;
    Integer number;
    String zipcode;
    UpdateUserResponseSectionGeolocation geolocation;
}
