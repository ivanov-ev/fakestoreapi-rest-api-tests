package models.shared;

import lombok.Data;

@Data
public class GenericUser { //Todo delete the shared folder
    String email, username, password;
    GenericUserNameSection name;
    GenericUserAddressSection address;
    String phone;
}
