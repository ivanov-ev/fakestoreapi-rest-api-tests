package models.updateUser;

import lombok.Data;
import models.shared.User;

@Data
public class UpdateUserRequest {
    String email, username, password;
    User.SectionName name;
    User.SectionAddress address;
    String phone;
}
