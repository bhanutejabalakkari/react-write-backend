package com.example.bloggybackend.controllers;

import com.example.bloggybackend.DTO.requests.CreatePostRequest;
import com.example.bloggybackend.models.Post;
import com.example.bloggybackend.models.User;
import com.example.bloggybackend.models.UserPrincipal;
import com.example.bloggybackend.repositories.UserRepository;
import com.example.bloggybackend.services.PostService;
import com.example.bloggybackend.services.UserService;
import com.example.bloggybackend.utils.ApiResponse;
import com.example.bloggybackend.utils.ApiResponseBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<List<Post>>> getAllPosts(HttpServletRequest request, HttpServletResponse response) {

        return ApiResponseBuilder.success(
                "Posts Fetched Successfully",
                HttpStatus.OK,
                postService.getPosts()
        );

    }

    @GetMapping("/post")
    public ResponseEntity<ApiResponse<Post>> getPostById(@RequestParam(value = "id") Integer id) {

        return ApiResponseBuilder.success(
                "Post Fetched Successfully",
                HttpStatus.FOUND,
                postService.getPostById(id)
        );


    }

    @PostMapping("/post")
    public ResponseEntity<ApiResponse<Post>> createPost(@Valid CreatePostRequest post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        return ApiResponseBuilder.success(
                "Post Created Successfully",
                HttpStatus.CREATED,
                postService.createPost(post, null)
        );

    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable(value = "id") Integer postId) {

        return ApiResponseBuilder.success(
                "Post deleted successfully",
                HttpStatus.OK,
                postService.deletePost(postId)
        );

    }


}
