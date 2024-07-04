package models.getUser;

import lombok.Data;
import models.deleteUser.DeleteUserRequestSectionGeolocation;

@Data
public class GetUserRequestSectionAddress {
    String city, street;
    Integer number;
    String zipcode;
    GetUserRequestSectionGeolocation geolocation;
}
