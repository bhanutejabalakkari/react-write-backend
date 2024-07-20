package com.example.bloggybackend.repositories;

import com.example.bloggybackend.models.Post;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {


    Post deletePostById(Integer postId);

    @Query(value = "SELECT p FROM Post p where p.active = true")
    List<Post> getAllPosts();

}
