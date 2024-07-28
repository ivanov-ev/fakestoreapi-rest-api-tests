package models.deleteUser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import models.shared.User;

@Data
public class DeleteUserResponse {
    Integer id;
    String email, username, password;
    User.SectionName name;
    User.SectionAddress address;
    String phone;

    @JsonProperty("__v")
    String v;
}
