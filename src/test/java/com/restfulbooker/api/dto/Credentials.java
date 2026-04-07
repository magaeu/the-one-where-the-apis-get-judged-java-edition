package com.restfulbooker.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Credentials {

    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

}
