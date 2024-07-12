package com.example.bloggybackend.services;

import com.example.bloggybackend.dtos.auth.responses.AuthenticationResponse;
import com.example.bloggybackend.dtos.auth.requests.SigninRequest;
import com.example.bloggybackend.dtos.auth.requests.SignupRequest;
import com.example.bloggybackend.models.Role;
import com.example.bloggybackend.models.User;
import com.example.bloggybackend.models.UserPrincipal;
import com.example.bloggybackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(SignupRequest request) {
        User user = User
                .builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(new UserPrincipal(user));

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(SigninRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        if (!authentication.isAuthenticated()) {
            System.out.println("User is invalid");
            throw new RuntimeException("User is invalid");
        }



        User user = userRepository.findByEmail(request.getEmail());

        String jwtToken = jwtService.generateToken(new UserPrincipal(user));

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
