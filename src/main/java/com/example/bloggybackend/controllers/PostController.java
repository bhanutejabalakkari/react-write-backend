package com.example.bloggybackend.controllers;

import com.example.bloggybackend.dto.requests.CreatePostRequest;
import com.example.bloggybackend.models.Post;
import com.example.bloggybackend.models.User;
import com.example.bloggybackend.repositories.UserRepository;
import com.example.bloggybackend.services.PostService;
import com.example.bloggybackend.utils.ApiResponse;
import com.example.bloggybackend.utils.ApiResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;

    private final UserRepository userRepository;

    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<List<Post>>> getAllPosts() {

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

    @PostMapping(path = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Post>> createPost(@Valid CreatePostRequest post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        return ApiResponseBuilder.success(
                "Post Created Successfully",
                HttpStatus.CREATED,
                postService.createPost(post, user)
        );

    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<Post>> deletePost(@PathVariable Integer postId) {

        return ApiResponseBuilder.success(
                "Post deleted successfully",
                HttpStatus.OK,
                postService.deletePost(postId)
        );

    }

    @PutMapping(value = "/post/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Post>> updatePost(
            @Valid CreatePostRequest request,
            @PathVariable(name = "postId") Integer postId
    ) {
        Post updatedPost = postService.updatePost(request, postId);
        return ApiResponseBuilder.success("Post Successfully Updated", HttpStatus.OK, updatedPost);
    }


}
