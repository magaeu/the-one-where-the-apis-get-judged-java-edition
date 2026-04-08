package com.restfulbooker.api.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.restfulbooker.api.dto.Credentials;
import com.restfulbooker.api.dto.Token;
import com.restfulbooker.api.setup.BaseAPI;

import io.restassured.response.Response;

public class AuthTest extends BaseAPI {

    @Test
    @DisplayName("Generate auth token")
    public void generateAuthToken() {

        Credentials credentials = Credentials.builder()
                .username("admin")
                .password("password123")
                .build();

        Token responseToken = given()
                .spec(getReq())
                .when()
                .body(credentials)
                .post("/auth")
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/authTokenSchema.json"))
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(Token.class);

        assertThat(responseToken).as("Token response is not null").isNotNull();
        assertThat(responseToken.token()).as("Token value is not empty").isNotEmpty();
    
    }

    @Test
    @DisplayName("Generate auth token with invalid credentials")
    public void generateAuthTokenWithInvalidCredentials() {

        Credentials invalidCredentials = Credentials.builder()
                .username("invalidUser")
                .password("invalidPass")
                .build();

        Response response = given()
                .spec(getReq())
                .when()
                .body(invalidCredentials)
                .post("/auth")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        String errorMessage = response.jsonPath().getString("reason");
        assertThat(errorMessage).as("Error message is 'Bad credentials'").isEqualTo("Bad credentials");

    }

}
