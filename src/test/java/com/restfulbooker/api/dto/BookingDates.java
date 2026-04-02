package com.restfulbooker.api.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class BookingDates {
    @JsonProperty("checkin")
    private Date checkIn;
    @JsonProperty("checkout")
    private Date checkOut;

}
