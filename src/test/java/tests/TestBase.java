package tests;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {
    @BeforeAll
    public static void configureRestAssured() {
        RestAssured.baseURI = "https://fakestoreapi.com/";
        RestAssured.basePath = "/";
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}
