package com.restfulbooker.api.tests;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.restfulbooker.api.dto.Credentials;
import com.restfulbooker.api.dto.Token;
import com.restfulbooker.api.setup.BaseTest;

public class AuthTest extends BaseTest {

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
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(Token.class);

        assertThat(responseToken).as("Token response is not null").isNotNull();
    
    }

    @Test
    @DisplayName("Generate auth token with invalid credentials")
    public void generateAuthTokenWithInvalidCredentials() {

        Credentials invalidCredentials = Credentials.builder()
                .username("invalidUser")
                .password("invalidPass")
                .build();

        given()
                .spec(getReq())
                .when()
                .body(invalidCredentials)
                .post("/auth")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

}
