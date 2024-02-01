package tests;

import io.restassured.http.ContentType;
import models.lobbok.UserBodyModelLomb;
import models.lobbok.UserRespModelLomb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTestsModel {
    @Test
    @Tag("api")
    @DisplayName("Create new user")
    void apiCreateNewUserTest() {
        UserBodyModelLomb authData = new UserBodyModelLomb();
        authData.setName("Dmitrii");
        authData.setJob("Engineer");
        UserRespModelLomb responce = given()
                .baseUri("https://reqres.in/")
                .contentType(ContentType.JSON)
                .body(authData)
                .when()
                .log().all()
                .post("/api/users")
                .then()
                .assertThat()
                .statusCode(201)
                .log().all()
                .extract().as(UserRespModelLomb.class);
                assertEquals("Dmitrii", responce.getName());
                assertEquals("Engineer", responce.getJob());



    }
}
