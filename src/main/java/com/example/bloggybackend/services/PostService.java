package com.example.bloggybackend.services;

import com.example.bloggybackend.DTO.requests.CreatePostRequest;
import com.example.bloggybackend.models.Post;
import com.example.bloggybackend.models.User;
import com.example.bloggybackend.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final CloudinaryService cloudinaryService;

    public List<Post> getPosts() {
        return postRepository.getAllPosts();
    }

    public Post getPostById(Integer postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createPost(CreatePostRequest request, User user) {
        String imageUrl = cloudinaryService.uploadImage(request.getImage(), "react-write");

        Post post = Post.builder()
                .title(request.getTitle())
                .slug(request.getSlug())
                .content(request.getContent())
                .active(request.getActive())
                .imageUrl(imageUrl)
                .user(user)
                .build();

        return postRepository.save(post);

    }

    public Post deletePost(Integer postId) {
        if (postRepository.existsById(postId)) return postRepository.deletePostById(postId);
        else throw new RuntimeException("Post Not Found With id "+ postId);
    }



}

