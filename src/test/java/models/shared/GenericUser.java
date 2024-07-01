package models.shared;

import lombok.Data;

@Data
public class GenericUser {
    String email, username, password;
    GenericUserNameSection name;
    GenericUserAddressSection address;
    String phone;
}
