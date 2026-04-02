package com.restfulbooker.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Booking {
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("totalprice")
    private int totalprice;
    @JsonProperty("depositpaid")
    private boolean depositpaid;
    @JsonProperty("bookingdates")
    private BookingDates bookingDates;
    @JsonProperty("additionalneeds")
    private String additionalNeeds;
}
