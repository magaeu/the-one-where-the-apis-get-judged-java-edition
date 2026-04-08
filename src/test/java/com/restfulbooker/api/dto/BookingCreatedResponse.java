package com.restfulbooker.api.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Jacksonized
@Accessors(fluent = true)
@Getter
@Setter
@NoArgsConstructor
public class BookingCreatedResponse {

    private BookingId bookingid;
    private Booking booking;
}
