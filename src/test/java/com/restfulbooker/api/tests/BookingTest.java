package com.restfulbooker.api.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.restfulbooker.api.dto.AuthPayload;
import com.restfulbooker.api.dto.AuthResponse;
import com.restfulbooker.api.dto.Booking;
import com.restfulbooker.api.dto.BookingCreatedResponse;
import com.restfulbooker.api.dto.BookingDates;
import com.restfulbooker.api.dto.BookingId;
import com.restfulbooker.api.dto.BookingResponse;
import com.restfulbooker.api.setup.BaseAPI;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

public class BookingTest extends BaseAPI {

    @Test
    @DisplayName("Get all bookings")
    public void getAllBookings() {

        List<BookingId> bookingResponse = given()
                .when()
                .get("/booking")
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/allBookingsSchema.json"))
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(new TypeRef<List<BookingId>>() {});

        assertThat(bookingResponse).as("Booking response is not null").isNotNull();
        assertThat(bookingResponse.size()).as("Booking response is not empty").isGreaterThan(0);
        assertThat(bookingResponse.get(0).bookingId()).as("Booking id is a match").isEqualTo(1);

    }

    @Test
    @DisplayName("Get booking by id")
    public void getBookingById() {

        BookingResponse bookingResponse = given()
                .spec(getReq())
                .when()
                .get("/booking/1")
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/getBookingSchema.json"))
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(BookingResponse.class);

        assertThat(bookingResponse).as("Booking response is not null").isNotNull();

    }

    @DisplayName("Get booking by invalid ids")
    @ParameterizedTest
    @ValueSource(strings =  {"9999", "a", "!@#$%^&*_+()."})
    public void getBookingByIdNotFound(String bookingId) {

        given()
            .spec(getReq())
            .pathParam("bookingId", bookingId)
        .when()
            .get("/booking/{bookingId}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);

    }

    @Test
    @DisplayName("Get booking by id with not acceptable header")
    public void getBookingByIdWithNotAcceptableHeader() {

        Response response = given()
            .spec(getReq())
            .header("Accept", MediaType.TEXT_PLAIN)
        .when()
            .get("/booking/1")
            .then()
            .statusCode(418)
            .extract()
            .response();

        assertThat(response.asString()).as("Response body is not empty").isNotEmpty();
        assertThat(response.asString()).as("Response body is a match").isEqualTo("I'm a Teapot");

    }

    @Test
    @DisplayName("Create booking with valid payload")
    public void createBookingWithValidPayload() {
        Booking bookingPayload = getBookingPayload();

        BookingResponse bookingResponse = given()
            .spec(getReq())
            .body(bookingPayload)
        .when()
            .post("/booking")
        .then()
            .body(matchesJsonSchemaInClasspath("schemas/createBookingSchema.json"))
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .body()
            .as(BookingResponse.class);

        assertThat(bookingResponse).as("Booking response is not null").isNotNull();
        assertThat(bookingResponse.booking().getFirstName()).as("Booking first name is a match").isEqualTo(getBookingPayload().getFirstName());
        assertThat(bookingResponse.booking().getLastName()).as("Booking last name is a match").isEqualTo(getBookingPayload().getLastName());

    }

    private Booking getBookingPayload() {
        BookingDates bookingDates = BookingDates.builder()
            .checkIn("2024-01-01")
            .checkOut("2024-01-10")
            .build();

        Booking bookingPayload = Booking.builder()
            .firstName("John")
            .lastName("Doe")
            .totalprice(150)
            .depositpaid(true)
            .bookingDates(bookingDates)
            .additionalNeeds("Breakfast")
            .build();
        return bookingPayload;
    }

    @Test
    @DisplayName("Delete booking by id with valid id and auth token")
    public void deleteBookingByIdWithValidIdAndAuthToken() {
        Booking bookingPayload = getBookingPayload();
        BookingCreatedResponse bookingResponse = getBookingResponse(bookingPayload);   

        AuthPayload credentials = getCredentials();
        AuthResponse responseToken = getResponseToken(credentials);

        given()
            .spec(getReq())
            .header("Cookie", "token=" + responseToken.token())
        .when()
            .delete("/booking/" + bookingResponse.bookingid().bookingId())
            .then()
            .statusCode(HttpStatus.SC_CREATED);

    }

    private BookingCreatedResponse getBookingResponse(Booking bookingPayload) {
        return given()
            .spec(getReq())
            .body(bookingPayload)
        .when()
            .post("/booking")
        .then()
            .extract()
            .body()
            .as(BookingCreatedResponse.class);
    }

    private AuthPayload getCredentials() {
        return AuthPayload.builder()
                .username("admin")
                .password("password123")
                .build();
    }

    private AuthResponse getResponseToken(AuthPayload credentials) {
        return given()
                .spec(getReq())
                .body(credentials)
                .when()
                .post("/auth")
                .then()
                .extract()
                .body()
                .as(AuthResponse.class);
    }

}
