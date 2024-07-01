package models;

import lombok.Data;

@Data
public class AddUserRequest{
    String email, username, password;
    AddUserRequestSectionName name;
    AddUserRequestSectionAddress address;
    String phone;
}
