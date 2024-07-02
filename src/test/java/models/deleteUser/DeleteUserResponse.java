package models.deleteUser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteUserResponse {
    Integer id;
    String email, username, password;
    DeleteUserRequestSectionName name;
    DeleteUserRequestSectionAddress address;
    String phone;

    @JsonProperty("__v")
    String v;
}
