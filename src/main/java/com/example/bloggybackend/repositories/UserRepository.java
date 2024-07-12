package com.example.bloggybackend.repositories;

import com.example.bloggybackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
