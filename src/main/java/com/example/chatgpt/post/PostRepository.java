package com.example.chatgpt.post;

import com.example.chatgpt.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAll();

    Optional<Post> findById(Long postId);

    @Query(value = "SELECT p FROM Post p WHERE p.chat.id IN ( SELECT c.id FROM Chat c WHERE c.user.id = ?1)")
    List<Post> selectPostsByUserId(Long userId);


    @Query("SELECT p, COUNT(l.post) AS likeCount FROM Post p LEFT JOIN Like1 l ON p = l.post GROUP BY p")
    List<Object[]> findAllWithLikeCount();

    @Query("SELECT p, COUNT(v.post) AS viewCount FROM Post p LEFT JOIN View1 v ON p = v.post GROUP BY p")
    List<Object[]> findAllWithViewCount();
}
