package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;

public class UserSpec {
    public final static RequestSpecification UserRequestSpec = with()
            .filter(withCustomTemplates())
            .log().method()
            .log().uri()
            .log().body();

    public final static RequestSpecification UserRequestWithJsonSpec = UserRequestSpec
            .contentType(JSON);

    public final static ResponseSpecification UserResponseWithJsonSpec200 = new ResponseSpecBuilder()
            .log(STATUS)
            .expectStatusCode(200)
            .expectContentType(JSON)
            .log(BODY)
            .build();
}
