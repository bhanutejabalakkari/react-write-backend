package com.example.bloggybackend.controllers;

import com.example.bloggybackend.dtos.auth.responses.AuthenticationResponse;
import com.example.bloggybackend.dtos.auth.requests.SigninRequest;
import com.example.bloggybackend.dtos.auth.requests.SignupRequest;
import com.example.bloggybackend.services.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(
        value = "/signup", 
        consumes = {"application/json", "application/x-www-form-urlencoded"}, 
        produces = {"application/json"}
    )
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping(
        value = "/signin", 
        consumes = {"application/json", "application/x-www-form-urlencoded"},
        produces = {"application/json"}
    )
    public ResponseEntity<AuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @GetMapping(
        value = "/user", 
        consumes = {"application/json", "application/x-www-form-urlencoded"},
        produces = {"application/json"}
    )
    public String getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return "The String is : " + userDetails.getUsername();
    }


}
