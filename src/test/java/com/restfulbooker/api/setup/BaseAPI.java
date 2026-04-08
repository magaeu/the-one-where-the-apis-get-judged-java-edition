package com.restfulbooker.api.setup;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseAPI {
    private static RequestSpecification req;
    private static ResponseSpecification resp;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @BeforeEach
    public void requestSpec() {
        req = new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @BeforeEach
    public void responseSpec() {
        resp = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();

    }

    @AfterAll
    public static void tearDown() {
        RestAssured.reset();
    }

    public static RequestSpecification getReq() {
        return req;
    }

    public static ResponseSpecification getResp() {
        return resp;
    }

}
