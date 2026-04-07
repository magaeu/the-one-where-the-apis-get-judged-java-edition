package com.restfulbooker.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BookingId {
    
    @JsonProperty("bookingid")
    private int bookingId;

}
