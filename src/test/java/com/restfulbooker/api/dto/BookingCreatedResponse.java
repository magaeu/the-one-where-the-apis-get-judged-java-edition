package com.restfulbooker.api.dto;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class BookingCreatedResponse {

    private BookingId bookingid;
    private Booking booking;
}
