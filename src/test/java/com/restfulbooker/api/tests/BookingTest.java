package com.restfulbooker.api.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.restfulbooker.api.dto.BookingId;
import com.restfulbooker.api.dto.BookingResponse;
import com.restfulbooker.api.setup.BaseAPI;

import io.restassured.common.mapper.TypeRef;


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

}
