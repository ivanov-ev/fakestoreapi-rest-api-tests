package tests;

import io.qameta.allure.*;
import models.addUser.*;
import models.deleteUser.DeleteUserRequest;
import models.deleteUser.DeleteUserResponse;
import models.getUser.GetUserResponse;
import models.updateUser.*;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.*;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static specs.AddUserSpec.AddUserRequestSpec;
import static specs.AddUserSpec.AddUserResponseSpec;
import static specs.DeleteUserSpec.DeleteUserRequestSpec;
import static specs.DeleteUserSpec.DeleteUserResponseSpec;
import static specs.GetUsersSpec.GetUserRequestSpec;
import static specs.GetUsersSpec.GetUserResponseSpec;
import static specs.UpdateUserSpec.UpdateUserRequestSpec;
import static specs.UpdateUserSpec.UpdateUserResponseSpec;

@Tag("rest_api_tests")
@DisplayName("Tests for the REST API methods related to the '/users' controller")
@Feature("fakestoreapi.com")
@Story("users")
@Owner("ivanov-ev")
@Severity(SeverityLevel.NORMAL)
public class UsersControllerTests extends TestBase {

    @Test
    @DisplayName("POST: Add a user")
    void addUserTest() {
        step("POST: Add a user", () -> {
            AddUserRequest jsonRequestBody = new AddUserRequest();
            prepareAddUserRequestBody(jsonRequestBody);
            AddUserResponse addUserResponse = step("Perform a POST request", () ->
                    given(AddUserRequestSpec)
                            .body(jsonRequestBody)
                            .when()
                            .post()
                            .then()
                            .spec(AddUserResponseSpec)
                            .assertThat()
                            .body("id", is(notNullValue())) //it duplicates the below assertion
                            .body(matchesJsonSchemaInClasspath("jsonSchemas/AddUserResponseSchema.json"))
                            .extract().as(AddUserResponse.class)
            );
            step("Check the response", () -> {
                        Assertions.assertFalse(addUserResponse.getId().toString().isEmpty());
                    }
            );
        });
    }

    @Test
    @DisplayName("PUT: Update a user")
    void updateUserTest() {
        step("PUT: Update a user", () -> {
            int id = 7;
            UpdateUserRequest jsonRequestBody = new UpdateUserRequest();
            prepareUpdateUserRequestBody(jsonRequestBody);
            UpdateUserResponse updateUserResponse = step("Perform a PUT request", () ->
                    given(UpdateUserRequestSpec)
                            .body(jsonRequestBody)
                            .when()
                            .put("/{id}", id)
                            .then()
                            .spec(UpdateUserResponseSpec)
                            .body(matchesJsonSchemaInClasspath("jsonSchemas/UpdateUserResponseSchema.json"))
                            .extract().as(UpdateUserResponse.class)
            );
            step("Check the response returns the same fields as the request body", () -> {
                        Assertions.assertEquals(jsonRequestBody.getEmail(), updateUserResponse.getEmail());
                        Assertions.assertEquals(jsonRequestBody.getUsername(), updateUserResponse.getUsername());
                        Assertions.assertEquals(jsonRequestBody.getPassword(), updateUserResponse.getPassword());
                        Assertions.assertEquals(jsonRequestBody.getName().getFirstname(), updateUserResponse.getName().getFirstname());
                        Assertions.assertEquals(jsonRequestBody.getName().getLastname(), updateUserResponse.getName().getLastname());
                        Assertions.assertEquals(jsonRequestBody.getAddress().getCity(), updateUserResponse.getAddress().getCity());
                        Assertions.assertEquals(jsonRequestBody.getAddress().getStreet(), updateUserResponse.getAddress().getStreet());
                        Assertions.assertEquals(jsonRequestBody.getAddress().getNumber(), updateUserResponse.getAddress().getNumber());
                        Assertions.assertEquals(jsonRequestBody.getAddress().getZipcode(), updateUserResponse.getAddress().getZipcode());
                        Assertions.assertEquals(jsonRequestBody.getAddress().getGeolocation().getLat(), updateUserResponse.getAddress().getGeolocation().getLat());
                        Assertions.assertEquals(jsonRequestBody.getAddress().getGeolocation().getLongitude(), updateUserResponse.getAddress().getGeolocation().getLongitude());
                        Assertions.assertEquals(jsonRequestBody.getPhone(), updateUserResponse.getPhone());
                        System.out.println(updateUserResponse);
                    }
            );
        });
    }

    @Test
    @DisplayName("DELETE: Delete a user")
    void deleteUserTest() {
        step("DELETE: Delete a user", () -> {
            int id = 6;
            DeleteUserResponse deleteUserResponse = step("Perform a DELETE request", () ->
                    given(DeleteUserRequestSpec)
                            .when()
                            .delete("/{id}", id)
                            .then()
                            .spec(DeleteUserResponseSpec)
                            .assertThat()
                            .body("id", is(notNullValue()))
                            .body(matchesJsonSchemaInClasspath("jsonSchemas/DeleteUserResponseSchema.json"))
                            .extract().as(DeleteUserResponse.class)
            );
        });
    }

    @Test
    @DisplayName("Get a single user")
    void getSingleUserTest() {
        step("GET: Get a user", () -> {
            int id = 1;
            GetUserResponse getUserResponse = step("Perform a GET request", () ->
                    given(GetUserRequestSpec)
                            .when()
                            .get("/{id}", id)
                            .then()
                            .spec(GetUserResponseSpec)
                            .assertThat()
                            .body("id", is(notNullValue()))
                            .body(matchesJsonSchemaInClasspath("jsonSchemas/GetUserResponseSchema.json"))
                            .extract().as(GetUserResponse.class)
            );
        });
    }


    //The below 3 tests will use the same model and schema (not sure about 1 vs N in schema

    @Test
    @DisplayName("Get all users")
    void getAllUsersTest() {
        GetUserResponse[] getUserResponseList = step("Perform a GET request", () ->
                given(GetUserRequestSpec)
                        .when()
                        .get()
                        .then()
                        .spec(GetUserResponseSpec)
                        .body(matchesJsonSchemaInClasspath("jsonSchemas/GetUsersResponseSchema.json"))
                        .extract().as(GetUserResponse[].class)
        );

        step("Check that user IDs are unique and non-blank", () -> {
                    ArrayList<Integer> returnedIds = new ArrayList<>();
                    for (GetUserResponse getUserResponse : getUserResponseList) {
                        Integer receivedId = getUserResponse.getId();
                        System.out.println("\nid = " + receivedId);
                        System.out.println("getUserResponse = " + getUserResponse);
                        Assertions.assertNotNull(receivedId, "ID is not blank");

                        returnedIds.add(receivedId);
                    }
                    System.out.println("Returned IDs = " + returnedIds + "\n");
                    Assertions.assertFalse(containsDuplicates(returnedIds), "Found user IDs have no duplicates");
                }
        );
    }

    //TODO make it a parameterized test
    //get("/users?limit=3"
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

    @Step("UpdateUserRequest: prepare a JSON request body")
    public UpdateUserRequest prepareUpdateUserRequestBody(UpdateUserRequest jsonRequestBody) {
        jsonRequestBody.setEmail("John@gmail.com");
        jsonRequestBody.setUsername("johnd");
        jsonRequestBody.setPassword("m38rmF$");

        UpdateUserRequestSectionAddress updateUserRequestSectionAddress = new UpdateUserRequestSectionAddress();
        updateUserRequestSectionAddress.setCity("kilcoole");
        updateUserRequestSectionAddress.setStreet("7835 new road");
        updateUserRequestSectionAddress.setNumber(3);
        updateUserRequestSectionAddress.setZipcode("12926-3874");

        UpdateUserRequestSectionGeolocation updateUserRequestSectionGeolocation =
                new UpdateUserRequestSectionGeolocation();
        updateUserRequestSectionGeolocation.setLat("-37.3159");
        updateUserRequestSectionGeolocation.setLongitude("81.1496");

        updateUserRequestSectionAddress.setGeolocation(updateUserRequestSectionGeolocation);
        jsonRequestBody.setAddress(updateUserRequestSectionAddress);

        UpdateUserRequestSectionName updateUserRequestSectionName = new UpdateUserRequestSectionName();
        updateUserRequestSectionName.setFirstname("John");
        updateUserRequestSectionName.setLastname("Doe");
        jsonRequestBody.setName(updateUserRequestSectionName);

        jsonRequestBody.setPhone("1-570-236-7033");
        System.out.println(jsonRequestBody);
        return jsonRequestBody;
    }

    //Todo: move to a helper file or move it to the test
    public static <T> boolean containsDuplicates(ArrayList<T> list) {
        Set<T> uniqueElements = new HashSet<>(list);
        return uniqueElements.size() != list.size();
    }
}
