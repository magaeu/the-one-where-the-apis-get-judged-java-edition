package com.restfulbooker.api.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.restfulbooker.api.dto.AuthPayload;
import com.restfulbooker.api.dto.AuthResponse;
import com.restfulbooker.api.setup.BaseAPI;

import io.restassured.response.Response;

public class AuthTest extends BaseAPI {

    @Test
    @DisplayName("Generate auth token")
    public void generateAuthToken() {

        AuthPayload credentials = AuthPayload.builder()
                .username("admin")
                .password("password123")
                .build();

        AuthResponse responseToken = given()
                .spec(getReq())
                .body(credentials)
                .when()
                .post("/auth")
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/authTokenSchema.json"))
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(AuthResponse.class);

        assertThat(responseToken).as("Token response is not null").isNotNull();
        assertThat(responseToken.token()).as("Token value is not empty").isNotEmpty();
    
    }

    @Test
    @DisplayName("Generate auth token with invalid credentials")
    public void generateAuthTokenWithInvalidCredentials() {

        AuthPayload invalidCredentials = AuthPayload.builder()
                .username("invalidUser")
                .password("invalidPass")
                .build();

        Response response = given()
                .spec(getReq())
                .body(invalidCredentials)
                .when()
                .post("/auth")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        String errorMessage = response.jsonPath().getString("reason");
        assertThat(errorMessage).as("Error message is 'Bad credentials'").isEqualTo("Bad credentials");

    }

}
