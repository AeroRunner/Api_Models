package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import static io.qameta.allure.Allure.step;

public class BaseTest {
    @BeforeAll
    public static void setpUp(){
        RestAssured.baseURI="https://reqres.in/";
    }
}
