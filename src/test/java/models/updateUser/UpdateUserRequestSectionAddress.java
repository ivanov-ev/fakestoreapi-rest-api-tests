package models.updateUser;

import lombok.Data;

@Data
public class UpdateUserRequestSectionAddress {
    String city, street;
    Integer number;
    String zipcode;
    UpdateUserRequestSectionGeolocation geolocation;
}
