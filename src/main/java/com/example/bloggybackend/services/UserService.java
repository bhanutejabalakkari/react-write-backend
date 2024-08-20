package com.example.bloggybackend.services;

import com.example.bloggybackend.dto.responses.AuthenticationResponse;
import com.example.bloggybackend.dto.requests.SigninRequest;
import com.example.bloggybackend.dto.requests.SignupRequest;
import com.example.bloggybackend.exception.CustomRuntimeException;
import com.example.bloggybackend.models.Role;
import com.example.bloggybackend.models.User;
import com.example.bloggybackend.models.UserPrincipal;
import com.example.bloggybackend.repositories.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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


    public AuthenticationResponse register(SignupRequest request, HttpServletResponse response) {

        User existedUser = userRepository.findByEmail(request.getEmail());

        if (existedUser != null) {
            throw new CustomRuntimeException("User with email already exists", HttpStatus.CONFLICT);
        }

        User user = User .builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(new UserPrincipal(user));

        Cookie cookie = new Cookie("token", jwtToken);

        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(2 * 24 * 60 * 60);

        response.addCookie(cookie);

        return AuthenticationResponse.builder().token(jwtToken).user(user).build();
    }

    public AuthenticationResponse authenticate(SigninRequest request, HttpServletResponse response) {

        System.out.println("In authenticate method");

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        if (!authentication.isAuthenticated()) {
            System.out.println("User is invalid");
            throw new RuntimeException("User is invalid");
        }
        
        User user = userRepository.findByEmail(request.getEmail());

        String jwtToken = jwtService.generateToken(new UserPrincipal(user));
        Cookie cookie = new Cookie("token", jwtToken);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(2 * 24 * 60 * 60);
        response.addCookie(cookie);


        return AuthenticationResponse.builder().token(jwtToken).user(user).build();
    }
}
