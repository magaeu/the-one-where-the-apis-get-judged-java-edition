package com.restfulbooker.api.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.restfulbooker.api.dto.BookingId;
import com.restfulbooker.api.dto.BookingResponse;
import com.restfulbooker.api.setup.BaseAPI;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.UseDataProvider;


public class BookingTest extends BaseAPI {

    @DataProvider
    public Object[][] bookingIds() {
        return new Object[][]{
                {"9999", "Non existing booking id"},
                {"a", "Non numeric booking id"},
                {"$%^&*(*&^%$#$%", "Special characters as booking id"},
                {"", "Empty string as booking id"},
        };
    }

    @Test
    @DisplayName("Get all bookings")
    public void getAllBookings() {

        BookingId[] bookingResponse = given()
                .when()
                .get("/booking")
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/allBookingsSchema.json"))
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(BookingId[].class);

        assertThat(bookingResponse).as("Booking response is not null").isNotNull();
        assertThat(bookingResponse.length).as("Booking response is not empty").isGreaterThan(0);
        assertThat(bookingResponse[0].bookingId()).as("Booking id is a match").isEqualTo(1);

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

    @Test
    @DisplayName("Get booking by invalid ids")
    @UseDataProvider("bookingIds")
    public void getBookingByIdNotFound(String bookingId, String description) {

        given()
            .spec(getReq())
            .pathParam("bookingId", bookingId)
        .when()
            .get("/booking/{bookingId}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);

    }

}
