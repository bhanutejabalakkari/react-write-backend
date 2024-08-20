package com.example.bloggybackend.services;

import com.example.bloggybackend.dto.requests.CreatePostRequest;
import com.example.bloggybackend.exception.CustomRuntimeException;
import com.example.bloggybackend.models.Post;
import com.example.bloggybackend.models.User;
import com.example.bloggybackend.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final CloudinaryService cloudinaryService;

    public List<Post> getPosts() {
        List<Post> posts = postRepository.getAllPosts(  );
        return posts.stream().peek((post) -> post.setImageUrl(cloudinaryService.generateSecureUrl(post.getImageUrl()))).collect(Collectors.toList());
    }

    public Post getPostById(Integer postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new CustomRuntimeException("Post is not found with given id", HttpStatus.NOT_FOUND);
        }
        Post post = postOptional.get();
        String secureUrl = cloudinaryService.generateSecureUrl(post.getImageUrl());
        post.setImageUrl(secureUrl);
        User user = post.getUser();
        // System.out.println(user);
        return post;
    }

    public Post createPost(CreatePostRequest request, User user) {

        if (user == null) {
            throw new CustomRuntimeException("User Does Not Exist", HttpStatus.NOT_FOUND);
        }

        String imagePublicId = cloudinaryService.uploadImage(request.getImage(), "react-write");
        if (imagePublicId == null || imagePublicId.isEmpty()) {
            throw new CustomRuntimeException("Could Not upload the image", HttpStatus.BAD_REQUEST);
        }

        Post post = Post.builder()
                .title(request.getTitle())
                .slug(request.getSlug())
                .content(request.getContent())
                .imageUrl(imagePublicId)
                .active(request.getActive())
                .user(user)
                .build();

        return postRepository.save(post);

    }

    @Transactional
    public Post updatePost(CreatePostRequest request, Integer postId) {
        Post originalPost = postRepository.findById(postId).orElseThrow(() -> new CustomRuntimeException("Post Not Found With Given Id", HttpStatus.NOT_FOUND));

        String imagePublicId = cloudinaryService.uploadImage(request.getImage(), "react-write");
        if (imagePublicId == null || imagePublicId.isEmpty()) {
            throw new CustomRuntimeException("Could Not Upload Image", HttpStatus.BAD_REQUEST);
        }
        cloudinaryService.deleteImage(originalPost.getImageUrl());
        originalPost.setImageUrl(imagePublicId);
        originalPost.setTitle(request.getTitle());
        originalPost.setActive(request.getActive());
        originalPost.setContent(request.getContent());
        originalPost.setSlug(request.getSlug());
        Post updatedPost = postRepository.save(originalPost);
        updatedPost.setImageUrl(cloudinaryService.generateSecureUrl(updatedPost.getImageUrl()));

        return updatedPost;

    }

    @Transactional
    public Post deletePost(Integer postId) {
        Post post = postRepository.getPostById(postId);
        if (post == null) {
            throw new CustomRuntimeException("Post Not Found", HttpStatus.NOT_FOUND);
        }
        cloudinaryService.deleteImage(post.getImageUrl());
        Integer id = postRepository.deletePostById(postId);
        log.info("The Post is deleted with post id --> {}", id);
        return post;
    }

}

