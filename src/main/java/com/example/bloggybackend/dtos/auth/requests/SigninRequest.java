package com.example.bloggybackend.dtos.auth.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

}
