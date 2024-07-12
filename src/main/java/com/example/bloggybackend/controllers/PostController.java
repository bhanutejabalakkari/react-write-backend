package com.example.bloggybackend.controllers;

import com.example.bloggybackend.dtos.auth.requests.CreatePostRequest;
import com.example.bloggybackend.models.Post;
import com.example.bloggybackend.response.ResponseHandler;
import com.example.bloggybackend.services.PostService;
import com.example.bloggybackend.utils.ApiResponse;
import com.example.bloggybackend.utils.ApiResponseBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<List<Post>>> getAllPosts(HttpServletRequest request, HttpServletResponse response) {
        

        return ResponseEntity.ok(ApiResponseBuilder.success(
                "Posts fetched Successfully",
                HttpStatus.OK,
                postService.getPosts()
        ));
    }

    @GetMapping("/post")
    public ResponseEntity<Post> getPostById(@RequestParam(value = "id") Integer id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.FOUND);
    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest post) {

        return ResponseEntity.ok(
            ApiResponseBuilder.success(
                "Post Created Successflly", 
                HttpStatus.CREATED, 
                post.getTitle()
            )
        );

        // Post createdPost = postService.createPost(post);
        // if (createdPost != null) {
        //     return ResponseHandler.generateResponse("Successfully created", HttpStatus.CREATED, createdPost);
        // }
        // return ResponseHandler.generateResponse("Error while creating the Post", HttpStatus.BAD_REQUEST, null);
    }

    @DeleteMapping("/post")
    public ResponseEntity<?> deletePost(@RequestBody JsonNode requestBody) {
        System.out.println("In delete post method");
        JsonNode idNode = requestBody.get("id");
        Integer id = idNode.asInt();
        try {
            postService.deletePost(id);
            return ResponseEntity.ok(ApiResponseBuilder.success(
                    "Post Deleted Successfully",
                    HttpStatus.NO_CONTENT,
                    null
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseBuilder.error(
                    "Post Not Found with given id",
                    HttpStatus.NOT_FOUND
            ));
        }

    }


}
