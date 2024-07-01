package models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import models.shared.GenericUser;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateUserRequest extends GenericUser {
}
