package models;

import lombok.Data;
import models.shared.GenericUserId;

import java.util.List;

@Data
public class GetUsersResponse {

List <GenericUserId> x; //TODO: this will not work. How can I add an unnamed list?
}
