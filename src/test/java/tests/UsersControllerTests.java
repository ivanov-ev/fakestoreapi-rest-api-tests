package tests;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.*;
import io.restassured.mapper.ObjectMapper;
import io.restassured.module.jsv.JsonSchemaValidator;
import models.*;
import models.shared.GenericUserAddressSection;
import models.shared.GenericUserAddressSectionGeolocationSubsection;
import models.shared.GenericUserNameSection;
import org.apache.commons.io.FileUtils;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Cookie;
import specs.*;

import java.io.File;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static specs.AddUserSpec.AddUserRequestSpec;
import static specs.AddUserSpec.AddUserResponseSpec;

@Tag("rest_api_tests")
@DisplayName("Tests for the REST API methods related to the '/users' controller")
@Feature("fakestoreapi.com")
@Story("users")
@Owner("ivanov-ev")
@Severity(SeverityLevel.NORMAL)
public class UsersControllerTests extends TestBase {

    @Test
    @DisplayName("Add a user")
    void addUserTest() {

        step("Add a user", () -> {
            AddUserRequest jsonRequestBody = new AddUserRequest();
            prepareAddUserRequestBody(jsonRequestBody);

            String jsonSchema = FileUtils.readFileToString(new File("src/test/resources/jsonSchemas/AddUserRequestSchema.json"), "UTF-8");
            System.out.println(jsonSchema);

            AddUserResponse addUserResponse = step("Perform a POST request", ()->
                    given(AddUserRequestSpec)
                            .body(jsonRequestBody)
                            //.body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/jsonSchemas/AddUserRequestSchema.json")))
                            //.body(JsonSchemaValidator.matchesJsonSchema(jsonSchema))
                            .body(matchesJsonSchemaInClasspath("jsonSchemas/AddUserRequestSchema.json"))
                    .when()
                            .post()
                    .then()
                            .spec(AddUserResponseSpec)
                            .body("$", hasKey("id")) //this assertion is no longer needed
                            //.assertThat().body("id", isA(Integer.class))
                            //.body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/jsonSchemas/AddUserResponseSchema.json"))) //TODO оно случайно работает, тут просто без разницы, сериализован или нет
                            .body(matchesJsonSchemaInClasspath("jsonSchemas/AddUserResponseSchema.json"))
                            .extract().as(AddUserResponse.class)
            );

            //The documentation is wrong, because I receive only 'id', not a huge json response
            step("Check the response", () -> {
                        //Assertions.assertFalse(addUserResponse.getId().isBlank());
                        Assertions.assertFalse(addUserResponse.getId().toString().isEmpty());
//                        Assertions.assertEquals(jsonRequestBody.getEmail(), addUserResponse.getEmail()); //these commands are no longer needed
//                        Assertions.assertEquals(jsonRequestBody.getUsername(), addUserResponse.getUsername());
//                        Assertions.assertEquals(jsonRequestBody.getPassword(), addUserResponse.getPassword());

                    }
            );
        });
    }

    @Test
    @DisplayName("Update a user")
    void updateUserTest() {

    }

    @Test
    @DisplayName("Delete a user")
    void deleteUserTest() {

    }

    @Test
    @DisplayName("Get all users")
    void getAllUsersTest() {

    }

    @Test
    @DisplayName("Get a single user")
    void getSingleUserTest() {

    }

    //TODO make it a parameterized test
    @Test
    @DisplayName("Get multiple users, but limited by the 'limit' query string parameter")
    void getUserLimitedTest() {

    }

    @Test
    @DisplayName("Get all users sorted in ascending and descending orders")
    void getAllUsersAscDescTest() {
        //TODO asc, then desc in a single run
    }




    //Add a negative test to get a error message instead of a JSON response



    @Step("AddUserRequest: prepare a JSON request body")
    public AddUserRequest prepareAddUserRequestBody(AddUserRequest jsonRequestBody) {
        jsonRequestBody.setEmail("John@gmail.com");
        jsonRequestBody.setUsername("johnd");
        jsonRequestBody.setPassword("m38rmF$");

        AddUserRequestSectionAddress addUserRequestSectionAddress = new AddUserRequestSectionAddress();
        addUserRequestSectionAddress.setCity("kilcoole");
        addUserRequestSectionAddress.setStreet("7835 new road");
        addUserRequestSectionAddress.setNumber(3);
        addUserRequestSectionAddress.setZipcode("12926-3874");

        AddUserRequestSectionGeolocation addUserRequestSectionGeolocation =
                new AddUserRequestSectionGeolocation();
        addUserRequestSectionGeolocation.setLat("-37.3159");
        addUserRequestSectionGeolocation.setLongitude("81.1496");

        addUserRequestSectionAddress.setGeolocation(addUserRequestSectionGeolocation);
        jsonRequestBody.setAddress(addUserRequestSectionAddress);

        AddUserRequestSectionName addUserRequestSectionName = new AddUserRequestSectionName();
        addUserRequestSectionName.setFirstname("John");
        addUserRequestSectionName.setLastname("Doe");
        jsonRequestBody.setName(addUserRequestSectionName);

        jsonRequestBody.setPhone("1-570-236-7033");
        System.out.println(jsonRequestBody);
        return jsonRequestBody;
    }
}
