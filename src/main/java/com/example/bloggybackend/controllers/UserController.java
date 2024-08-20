package com.example.bloggybackend.controllers;

import com.example.bloggybackend.dto.responses.AuthenticationResponse;
import com.example.bloggybackend.dto.requests.SigninRequest;
import com.example.bloggybackend.dto.requests.SignupRequest;
import com.example.bloggybackend.models.User;
import com.example.bloggybackend.repositories.UserRepository;
import com.example.bloggybackend.services.UserService;


import com.example.bloggybackend.utils.ApiResponse;
import com.example.bloggybackend.utils.ApiResponseBuilder;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = "/api/v1",
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE},
        produces = { MediaType.APPLICATION_JSON_VALUE }
)
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping(path = "/signout")
    public ResponseEntity<ApiResponse<Object>> signout(
            @CookieValue(name = "token", required = true, defaultValue = "") String cookie,
            HttpServletResponse response
    ) {
        log.info("The Cookie Value is --> {}", cookie);
        if (!cookie.isEmpty()) {
            Cookie cookie1 = new Cookie("token", null);
            cookie1.setMaxAge(0);
            cookie1.setHttpOnly(true);
            cookie1.setSecure(true);
            cookie1.setPath("/");
            response.addCookie(cookie1);
            log.info("The Cookie is removed");
        }
        return ApiResponseBuilder.success("Successfully Signed Out", HttpStatus.OK);
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> signup(@RequestBody SignupRequest request, HttpServletResponse response) {

        return ApiResponseBuilder.success(
                "User Signed Up successfully",
                HttpStatus.OK,
                userService.register(request, response)
        );

    }

    @PostMapping(path = "/signin")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> signin(@RequestBody SigninRequest request, HttpServletResponse response) {


        return ApiResponseBuilder.success(
                "User Signed In successfully",
                HttpStatus.OK,
                userService.authenticate(request, response)
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
