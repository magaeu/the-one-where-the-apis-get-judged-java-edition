package com.restfulbooker.api.tests;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.restfulbooker.api.dto.Booking;
import com.restfulbooker.api.dto.BookingDates;
import com.restfulbooker.api.dto.BookingResponse;
import com.restfulbooker.api.setup.BaseTest;

import io.restassured.specification.RequestSpecification;

class BookingTest extends BaseTest {
    private Booking booking;
    private BookingDates bookingDates;
    private BookingResponse bookingResponse;

    @Test
    @DisplayName("Create a booking with valid data")
    void createBookingWithValidData() {
        bookingDates = new BookingDates(java.sql.Date.valueOf("2024-07-01"), java.sql.Date.valueOf("2024-07-10"));
        booking = new Booking("John", "Doe", (int) 150.0, true, bookingDates, "Breakfast included");
        

        bookingResponse = (BookingResponse) ((RequestSpecification) given()
                .contentType("application/json")
                .when()
                .body(booking)
                .post("/booking"))
                .log().all()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .expect().response();

        assertThat(bookingResponse.getBookingId()).as("Booking ID is not null").isNot(null);

    }
}
