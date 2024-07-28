package models.addUser;

import lombok.Data;
import models.shared.User;

@Data
public class AddUserRequest {
    String email, username, password;
    User.SectionName name;
    User.SectionAddress address;
    String phone;
}
