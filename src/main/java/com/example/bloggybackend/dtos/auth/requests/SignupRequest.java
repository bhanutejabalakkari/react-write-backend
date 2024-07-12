package com.example.bloggybackend.dtos.auth.requests;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    private String name;

    private String email;

    private String password;

}
