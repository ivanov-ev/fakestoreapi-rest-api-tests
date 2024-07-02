package models.deleteUser;

import lombok.Data;

@Data
public class DeleteUserRequestSectionAddress {
    String city, street;
    Integer number;
    String zipcode;
    DeleteUserRequestSectionGeolocation geolocation;
}
