package com.example.bloggybackend.repositories;

import com.example.bloggybackend.models.Post;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {


    Integer deletePostById(Integer postId);

    @Query(value = "SELECT p FROM Post p JOIN FETCH p.user where p.id = :postId")
    Post getPostWithUser(Integer postId);

    @Query(value = "SELECT p FROM Post p where p.active = true")
    List<Post> getAllPosts();

    @Query(value = "SELECT p from Post p WHERE p.id = :postId")
    Post getPostById(Integer postId);
}
