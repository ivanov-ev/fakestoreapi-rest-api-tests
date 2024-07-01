package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class DeleteUserSpec {

    public static RequestSpecification DeleteUserRequestSpec = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().method()
            .log().uri()
            .log().body()
            .basePath("/users");

    public static ResponseSpecification DeleteUserResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .expectStatusCode(200)
            .log(BODY)
            .build();
}
