package models.shared;

import lombok.Data;
import lombok.EqualsAndHashCode;
import models.shared.GenericUser;

@EqualsAndHashCode(callSuper = true)
@Data
public class GenericUserId extends GenericUser {
    String id;
}
