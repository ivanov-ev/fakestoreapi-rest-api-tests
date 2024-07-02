package models.addUser;

import lombok.Data;

@Data
public class AddUserRequestSectionAddress {
    String city, street;
    Integer number;
    String zipcode;
    AddUserRequestSectionGeolocation geolocation;
}
