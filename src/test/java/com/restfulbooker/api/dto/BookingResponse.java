package com.restfulbooker.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Accessors(fluent = true)
@Getter
@Setter
public class BookingResponse {

    private Booking booking;

}
