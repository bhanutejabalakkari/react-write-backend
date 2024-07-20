package com.example.bloggybackend.controllers;

import com.example.bloggybackend.DTO.responses.AuthenticationResponse;
import com.example.bloggybackend.DTO.requests.SigninRequest;
import com.example.bloggybackend.DTO.requests.SignupRequest;
import com.example.bloggybackend.models.User;
import com.example.bloggybackend.models.UserPrincipal;
import com.example.bloggybackend.repositories.UserRepository;
import com.example.bloggybackend.services.UserService;


import com.example.bloggybackend.utils.ApiResponse;
import com.example.bloggybackend.utils.ApiResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//"application/json"
@RestController
@RequestMapping(
        path = "/api/v1"
//        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE},
//        produces = { MediaType.APPLICATION_JSON_VALUE }
)
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping(path = "/signup")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> signup(@RequestBody SignupRequest request) {

        return ApiResponseBuilder.success(
                "User Signed Up successfully",
                HttpStatus.OK,
                userService.register(request)
        );

    }

    @PostMapping(path = "/signin")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> signin(@RequestBody SigninRequest request) {

        System.out.println("In Sign In method");

        return ApiResponseBuilder.success(
                "User Signed In successfully",
                HttpStatus.OK,
                userService.authenticate(request)
        );

    }

    @GetMapping(path = "/user")
    public ResponseEntity<ApiResponse<User>> getUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);


        return ApiResponseBuilder.success(
                "Successfully Fetched User Details",
                HttpStatus.OK,
                user
        );
    }


}
