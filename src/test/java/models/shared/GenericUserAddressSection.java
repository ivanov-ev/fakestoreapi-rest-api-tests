package models.shared;

import lombok.Data;

@Data
public class GenericUserAddressSection {
    String city, street, number, zipcode;
    GenericUserAddressSectionGeolocationSubsection geolocation;
}
