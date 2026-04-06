package com.restfulbooker.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookingCreatedResponse {

    @JsonProperty("bookingid")
    private int bookingId;
    private Booking booking;
}
