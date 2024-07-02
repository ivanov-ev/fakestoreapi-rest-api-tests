package models.updateUser;

import lombok.Data;

@Data
public class UpdateUserResponse {
    String email, username, password;
    UpdateUserResponseSectionName name;
    UpdateUserResponseSectionAddress address;
    String phone;
}
