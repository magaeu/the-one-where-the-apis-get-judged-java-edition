package com.restfulbooker.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Accessors(fluent = true)
@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {

    @JsonProperty("token")
    private String token;

}
