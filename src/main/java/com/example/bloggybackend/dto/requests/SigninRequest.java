package com.example.bloggybackend.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
    @Email(message = "Please provide a valid email")
    private String email;

    @JsonProperty("password")
    @NotNull(message = "Please provide password")
    private String password;

}
