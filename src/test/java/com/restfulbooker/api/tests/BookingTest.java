package com.restfulbooker.api.tests;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.restfulbooker.api.dto.BookingResponse;
import com.restfulbooker.api.setup.BaseTest;

class BookingTest extends BaseTest {

    @Test
    @DisplayName("Get all bookings")
    public void getAllBookings() {

        BookingResponse[] bookingResponse = given()
                .when()
                .get("/booking")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(BookingResponse[].class);    
        
        assertThat(bookingResponse).as("Booking response is not empty").isNotEmpty();
        assertThat(bookingResponse).as("Booking response is greater than 0").hasSizeGreaterThan(0); 
        
    }

}
