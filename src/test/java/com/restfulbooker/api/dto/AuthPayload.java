package com.restfulbooker.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthPayload {

    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

}
