package tests;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestBase {

    static Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeAll
    public static void configureRestAssured() {
        RestAssured.baseURI = "https://fakestoreapi.com/";
        RestAssured.basePath = "/";
        RestAssured.defaultParser = Parser.JSON;
    }
}
