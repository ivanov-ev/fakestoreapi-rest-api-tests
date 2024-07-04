package models.getUser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetUserResponse {
    Integer id;
    String email, username, password;
    GetUserRequestSectionName name;
    GetUserRequestSectionAddress address;
    String phone;

    @JsonProperty("__v")
    String v;
}
