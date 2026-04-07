package com.restfulbooker.api.tests;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.restfulbooker.api.dto.Booking;
import com.restfulbooker.api.dto.BookingDates;
import com.restfulbooker.api.dto.BookingId;
import com.restfulbooker.api.dto.BookingResponse;
import com.restfulbooker.api.setup.BaseTest;


class BookingTest extends BaseTest {

    @Test
    @DisplayName("Get all bookings")
    public void getAllBookings() {

        BookingId[] bookingResponse = given()
                .when()
                .get("/booking")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(BookingId[].class);    
        
        assertThat(bookingResponse).as("Booking response is not null").isNotNull();
        assertThat(bookingResponse.length).as("Booking response is not empty").isGreaterThan(0);
        
    }

    @Test
    @DisplayName("Get booking by id")
    public void getBookingById() {

        BookingResponse bookingResponse = given()
                .when()
                .get("/booking/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(BookingResponse.class);    
        
        assertThat(bookingResponse).as("Booking response is not null").isNotNull();
        assertThat(bookingResponse.getBooking().getFirstName()).as("Booking firstname is a match").isEqualTo("Jim");
        assertThat(bookingResponse.getBooking().getLastName()).as("Booking last name is a match").isEqualTo("Ericsson");
        
    }

}
