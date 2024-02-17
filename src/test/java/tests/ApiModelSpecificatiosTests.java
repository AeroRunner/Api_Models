package tests;

import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static spec.Specificatios.*;

@Tag("api")
public class ApiModelSpecificatiosTests extends BaseTest {
    @Test
    @DisplayName("Create new user")
    void apiCreateNewUserTest() {
        UserRequestAndResponseModel authData = new UserRequestAndResponseModel();
        authData.setName("Dmitrii");
        authData.setJob("Engineer");
        UserRequestAndResponseModel userModel = step("Sending a request to create a new user", () ->
                given(createUserReqSpec)
                        .body(authData)
                        .when()
                        .post()
                        .then()
                        .assertThat()
                        .spec(createUserRespSpec)
                        .extract().as(UserRequestAndResponseModel.class
                        ));
        step("checking the response", () -> {
            assertEquals("Dmitrii", userModel.getName());
            assertEquals("Engineer", userModel.getJob());
        });
    }

    @Test
    @DisplayName("check code 404")
    void apiCheckErrorCodeTest() {
        step("Sending a request to view a non-existent user", () ->
                given(getUnknownUserReq)
                        .when()
                        .get()
                        .then()
                        .assertThat()
                        .spec(getUnknownUserResp));
    }

    @Test
    @DisplayName("Delete User")
    void apiDeleteUserTest() {
        step("remove the user from the platform", () ->
                given(deleteUserReqSpec)
                        .when()
                        .log().all()
                        .delete()
                        .then()
                        .assertThat()
                        .spec(deleteuserRespSpec)
        );
    }

    @Test
    @DisplayName("Update user")
    void apiUpdateUserTest() {
        UserRequestAndResponseModel updateData = new UserRequestAndResponseModel();
        updateData.setName("Dmitrii Elizarov");
        updateData.setJob("Senior Engineer");
        UserRequestAndResponseModel userModel = step("Sending a request to create a user", () ->
                given(updateUserReqSpec)
                        .body(updateData)
                        .when()
                        .put()
                        .then()
                        .assertThat()
                        .spec(updateUserRespSpec)
                        .extract().as(UserRequestAndResponseModel.class
                        ));
        step("check response", () -> {
            assertEquals("Dmitrii Elizarov", userModel.getName());
            assertEquals("Senior Engineer", userModel.getJob());
        });
    }

    @Test
    @DisplayName("Registration Test")
    void apiRegisterEmailTest() {
        RegistrationRequestModel regData = new RegistrationRequestModel();
        regData.setEmail("eve.holt@reqres.in");
        regData.setPassword("pistol");
        RegistrationResponseModel registrationModel = step("register a user using email and password", () ->
                given(registrationReqSpec)
                        .body(regData)
                        .when()
                        .post()
                        .then()
                        .assertThat()
                        .spec(registrationRespSpec)
                        .extract().as(RegistrationResponseModel.class
                        ));

        step("We check successful registration with the received ID and token", () -> {
            assertEquals("4", registrationModel.getId());
            assertNotNull(registrationModel.getToken());
        });

    }
}
