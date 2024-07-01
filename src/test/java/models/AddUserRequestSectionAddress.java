package models;

import lombok.Data;

@Data
public class AddUserRequestSectionAddress {
    String city, street, number, zipcode;
    AddUserRequestSectionGeolocation geolocation;
}
