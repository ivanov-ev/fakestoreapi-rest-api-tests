package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import models.addUser.*;
import models.deleteUser.DeleteUserResponse;
import models.getUser.GetUserResponse;
import models.updateUser.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static helpers.ArrayListChecker.containsDuplicates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static specs.UserSpec.*;

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
            AddUserRequest addUserRequest = addUserRequestWithData();
            logger.info(String.valueOf(addUserRequest));
            AddUserResponse addUserResponse = step("Perform a POST request", () ->
                    given(UserRequestWithJsonSpec)
                            .body(addUserRequest)
                            .when()
                            .post()
                            .then()
                            .spec(UserResponseWithJsonSpec200)
                            .body(matchesJsonSchemaInClasspath(
                                    "jsonSchemas/AddUserResponseSchema.json"))
                            .extract().as(AddUserResponse.class)
            );
            step("Check the response has an alpha-numeric ID, and its length is equal or more than 1", () -> {
                        assertThat(addUserResponse.getId().toString()).isAlphanumeric()
                                .hasSizeGreaterThanOrEqualTo(1);
                    }
            );
        });
    }

    @Test
    @DisplayName("PUT: Update a user")
    void updateUserTest() {
        step("PUT: Update a user", () -> {
            int id = 7;
            UpdateUserRequest updateUserRequest = updateUserRequestWithData();
            logger.info(String.valueOf(updateUserRequest));
            UpdateUserResponse updateUserResponse = step("Perform a PUT request", () ->
                    given(UserRequestWithJsonSpec)
                            .body(updateUserRequest)
                            .when()
                            .put("/{id}", id)
                            .then()
                            .spec(UserResponseWithJsonSpec200)
                            .body(matchesJsonSchemaInClasspath(
                                    "jsonSchemas/UpdateUserResponseSchema.json"))
                            .extract().as(UpdateUserResponse.class)
            );
            step("Check the response returns the same values as the request body", () -> {
                        logger.info(String.valueOf(updateUserResponse));
                        step(String.valueOf(updateUserResponse));
                        assertUpdateUserReqAndRes(updateUserRequest, updateUserResponse);
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
                    given(UserRequestSpec)
                            .when()
                            .delete("/{id}", id)
                            .then()
                            .spec(UserResponseWithJsonSpec200)
                            .assertThat()
                            .body("id", is(notNullValue()))
                            .body(matchesJsonSchemaInClasspath(
                                    "jsonSchemas/DeleteUserResponseSchema.json"))
                            .extract().as(DeleteUserResponse.class)
            );
            step("Check the response ID is equal to the request ID", () -> {
                        logger.info("Returned ID = " + deleteUserResponse.getId());
                        step("Returned ID = " + deleteUserResponse.getId());
                        Assertions.assertEquals(id, deleteUserResponse.getId(),
                                "The response contains the ID of the deleted user");
                    }
            );
        });
    }

    @Test
    @DisplayName("GET: Get a single user")
    void getSingleUserTest() {
        step("GET: Get a single user", () -> {
            int id = 1;
            GetUserResponse getUserResponse = step("Perform a GET request", () ->
                    given(UserRequestSpec)
                            .when()
                            .get("/{id}", id)
                            .then()
                            .spec(UserResponseWithJsonSpec200)
                            .assertThat()
                            .body("id", is(notNullValue()))
                            .body(matchesJsonSchemaInClasspath(
                                    "jsonSchemas/GetUserResponseSchema.json"))
                            .extract().as(GetUserResponse.class)
            );
            step("Check the response ID is equal to the request ID", () -> {
                        logger.info("Returned ID = " + getUserResponse.getId());
                        step("Returned ID = " + getUserResponse.getId());
                        Assertions.assertEquals(id, getUserResponse.getId(),
                                "The response contains the ID of the requested user");
                    }
            );
            step("Check the other fields in the response data", () -> {
                        Assertions.assertEquals(7682, getUserResponse.getAddress().getNumber());
                        Assertions.assertEquals("john@gmail.com", getUserResponse.getEmail());
                        Assertions.assertEquals("johnd", getUserResponse.getUsername());
                        Assertions.assertEquals("m38rmF$", getUserResponse.getPassword());
                        Assertions.assertEquals("john", getUserResponse.getName().getFirstname());
                        Assertions.assertEquals("doe", getUserResponse.getName().getLastname());
                        Assertions.assertEquals("1-570-236-7033", getUserResponse.getPhone());
                        Assertions.assertEquals("kilcoole", getUserResponse.getAddress().getCity());
                        Assertions.assertEquals("new road", getUserResponse.getAddress().getStreet());
                        Assertions.assertEquals("12926-3874", getUserResponse.getAddress().getZipcode());
                        Assertions.assertEquals("-37.3159", getUserResponse.getAddress().getGeolocation().getLat());
                        Assertions.assertEquals("81.1496", getUserResponse.getAddress().getGeolocation().getLongitude());
                    }
            );
        });
    }

    @Test
    @DisplayName("GET: Get all users")
    void getAllUsersTest() {
        GetUserResponse[] getUserResponseList = step("Perform a GET request", () ->
                given(UserRequestSpec)
                        .when()
                        .get()
                        .then()
                        .spec(UserResponseWithJsonSpec200)
                        .body(matchesJsonSchemaInClasspath(
                                "jsonSchemas/GetUsersResponseSchema.json"))
                        .extract().as(GetUserResponse[].class)
        );
        step("Check that user IDs are unique and non-blank", () -> {
                    ArrayList<Integer> returnedIds = new ArrayList<>();
                    for (GetUserResponse getUserResponse : getUserResponseList) {
                        Integer receivedId = getUserResponse.getId();
                        logger.info("id = " + receivedId);
                        logger.info("getUserResponse = " + getUserResponse);
                        step("id = " + receivedId);
                        step("getUserResponse = " + getUserResponse);
                        Assertions.assertNotNull(receivedId, "ID is not blank");
                        returnedIds.add(receivedId);
                    }
                    logger.info("Returned IDs = " + returnedIds);
                    step("Returned IDs = " + returnedIds);
                    Assertions.assertFalse(containsDuplicates(returnedIds),
                            "Found user IDs have no duplicates");
                }
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 5000})
    @DisplayName("GET: Get multiple users limited by the 'limit' query string parameter")
    void getUserLimitedTest(int limit) {
        GetUserResponse[] getUserResponseList = step("Perform a GET request", () ->
                given(UserRequestSpec)
                        .queryParam("limit", limit)
                        .when()
                        .get()
                        .then()
                        .spec(UserResponseWithJsonSpec200)
                        .body(matchesJsonSchemaInClasspath(
                                "jsonSchemas/GetUsersResponseSchema.json"))
                        .extract().as(GetUserResponse[].class)
        );
        step("Check the number of found users does not exceed the limit", () -> {
                    Assertions.assertTrue(getUserResponseList.length <= limit,
                            "The number of objects <= the limit parameter");
                }
        );
    }

    @Test
    @DisplayName("GET: Get all users sorted in ascending order")
    void getAllUsersAscTest() {
        GetUserResponse[] getUserResponseAscList = step("Perform a GET request", () ->
                given(UserRequestSpec)
                        .queryParam("sort", "asc")
                        .when()
                        .get()
                        .then()
                        .spec(UserResponseWithJsonSpec200)
                        .body(matchesJsonSchemaInClasspath(
                                "jsonSchemas/GetUsersResponseSchema.json"))
                        .extract().as(GetUserResponse[].class)
        );
        step("Check that IDs are sorted in ascending order", () -> {
                    List<Integer> returnedIdsBeforeSort = Arrays.stream(getUserResponseAscList).
                            map(GetUserResponse::getId).toList();
                    logger.info("Actual sorting = " + returnedIdsBeforeSort);
                    step("Actual sorting = " + returnedIdsBeforeSort);

                    List<Integer> returnedIdsAfterAscSort = new ArrayList<>(returnedIdsBeforeSort);
                    returnedIdsAfterAscSort.sort(Comparator.naturalOrder());
                    logger.info("Target sorting = " + returnedIdsAfterAscSort);
                    step("Target sorting = " + returnedIdsAfterAscSort);

                    Assertions.assertEquals(returnedIdsBeforeSort, returnedIdsAfterAscSort,
                            "The actual sorting is ASC");
                }
        );
    }

    @Test
    @DisplayName("GET: Get all users sorted in descending order")
    void getAllUsersDescTest() {
        GetUserResponse[] getUserResponseDescList = step("Perform a GET request", () ->
                given(UserRequestSpec)
                        .queryParam("sort", "desc")
                        .when()
                        .get()
                        .then()
                        .spec(UserResponseWithJsonSpec200)
                        .body(matchesJsonSchemaInClasspath(
                                "jsonSchemas/GetUsersResponseSchema.json"))
                        .extract().as(GetUserResponse[].class)
        );
        step("Check that IDs are sorted in descending order", () -> {
                    List<Integer> returnedIdsBeforeSort = Arrays.stream(getUserResponseDescList).
                            map(GetUserResponse::getId).toList();
                    logger.info("Actual sorting = " + returnedIdsBeforeSort);
                    step("Actual sorting = " + returnedIdsBeforeSort);

                    List<Integer> returnedIdsAfterDescSort = new ArrayList<>(returnedIdsBeforeSort);
                    returnedIdsAfterDescSort.sort(Comparator.reverseOrder());
                    logger.info("Target sorting = " + returnedIdsAfterDescSort);
                    step("Target sorting = " + returnedIdsAfterDescSort);

                    Assertions.assertEquals(returnedIdsBeforeSort, returnedIdsAfterDescSort,
                            "The actual sorting is DESC");
                }
        );
    }

    private void assertUpdateUserReqAndRes(UpdateUserRequest req, UpdateUserResponse res) {
        Assertions.assertEquals(req.getEmail(), res.getEmail());
        Assertions.assertEquals(req.getUsername(), res.getUsername());
        Assertions.assertEquals(req.getPassword(), res.getPassword());
        Assertions.assertEquals(req.getName().getFirstname(), res.getName().getFirstname());
        Assertions.assertEquals(req.getName().getLastname(), res.getName().getLastname());
        Assertions.assertEquals(req.getAddress().getCity(), res.getAddress().getCity());
        Assertions.assertEquals(req.getAddress().getStreet(), res.getAddress().getStreet());
        Assertions.assertEquals(req.getAddress().getNumber(), res.getAddress().getNumber());
        Assertions.assertEquals(req.getAddress().getZipcode(), res.getAddress().getZipcode());
        Assertions.assertEquals(req.getAddress().getGeolocation().getLat(), res.getAddress().getGeolocation().getLat());
        Assertions.assertEquals(req.getAddress().getGeolocation().getLongitude(), res.getAddress().getGeolocation()
                .getLongitude());
        Assertions.assertEquals(req.getPhone(), res.getPhone());
    }

    private static AddUserRequest addUserRequestWithData() {
        AddUserRequest addUserRequest = new AddUserRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File jsonFile = new File("src/test/resources/json/AddUserReq.json");
            addUserRequest = objectMapper.readValue(jsonFile, AddUserRequest.class);
        } catch (IOException e) {
            logger.error("Cannot parse the JSON file");
            step("Cannot parse the JSON file");
        }
        return addUserRequest;
    }

    private static UpdateUserRequest updateUserRequestWithData() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File jsonFile = new File("src/test/resources/json/UpdateUserReq.json");
            updateUserRequest = objectMapper.readValue(jsonFile, UpdateUserRequest.class);
        } catch (IOException e) {
            logger.error("Cannot parse the JSON file");
            step("Cannot parse the JSON file");
        }
        return updateUserRequest;
    }
}
