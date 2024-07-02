package models.updateUser;

import lombok.Data;

@Data
public class UpdateUserRequest {
    String email, username, password;
    UpdateUserRequestSectionName name;
    UpdateUserRequestSectionAddress address;
    String phone;
}
