package models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import models.shared.GenericUserId;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeleteUserResponse extends GenericUserId {
}
