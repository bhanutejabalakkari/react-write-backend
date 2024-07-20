package com.example.bloggybackend.DTO.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank(message = "Please provide a valid name")
    private String name;

    @NotBlank(message = "Please provide a valid email")
    private String email;

    @NotBlank(message = "Please provide a password")
    private String password;

}
