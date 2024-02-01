package tests;

import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static spec.Spec.*;

public class ApiModelSpecTests extends BaseTest {
    @Test
    @Tag("api")
    @DisplayName("Create new user")
    void apiCreateNewUserTest() {
        CreateUserRequestModel authData = new CreateUserRequestModel();
        authData.setName("Dmitrii");
        authData.setJob("Engineer");
        CreateUserResponseModel responce = step("Sending a request to create a user", () ->
                given(createUserReqSpec)
                        .body(authData)
                        .when()
                        .post()
                        .then()
                        .assertThat()
                        .spec(createUserRespSpec)
                        .extract().as(CreateUserResponseModel.class
                        ));
        step("checking the response", () -> {
            assertEquals("Dmitrii", responce.getName());
            assertEquals("Engineer", responce.getJob());
        });
    }

    @Test
    @Tag("api")
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
    @Tag("api")
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
    @Tag("api")
    @DisplayName("Update user")
    void apiUpdateUserTest() {
        UpdateUserRequestModel updateData = new UpdateUserRequestModel();
        updateData.setName("Dmitrii Elizarov");
        updateData.setJob("Senior Engineer");
        UpdateUserResponseModel response = step("Sending a request to create a user", () ->
                given(updateUserReqSpec)
                        .body(updateData)
                        .when()
                        .put()
                        .then()
                        .assertThat()
                        .spec(updateUserRespSpec)
                        .extract().as(UpdateUserResponseModel.class
                        ));
        step("check response", () -> {
            assertEquals("Dmitrii Elizarov", response.getName());
            assertEquals("Senior Engineer", response.getJob());
        });
        assertEquals("Dmitrii Elizarov", response.getName());
        assertEquals("Senior Engineer", response.getJob());

    }

    @Test
    @Tag("api")
    @DisplayName("Registration Test")
    void apiRegisterEmailTest() {
        RegistrationRequestModel regData = new RegistrationRequestModel();
        regData.setEmail("eve.holt@reqres.in");
        regData.setPassword("pistol");
        RegistrationResponseModel response = step("register a user using email and password", () ->
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
            assertEquals("4", response.getId());
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });

    }
}
