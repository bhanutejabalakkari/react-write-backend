package com.example.bloggybackend.services;

import com.example.bloggybackend.models.User;
import com.example.bloggybackend.models.UserPrincipal;
import com.example.bloggybackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("user details service not called");

        User user = userRepository.findByEmail(email);
        System.out.println("user details service  called");


        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        }

        System.out.println("In user details service after finding email");

        return new UserPrincipal(user);
    }

}
