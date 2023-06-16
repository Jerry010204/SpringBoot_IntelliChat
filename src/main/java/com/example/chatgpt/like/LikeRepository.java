package com.example.chatgpt.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public interface LikeRepository extends JpaRepository<Like1, Long> {

    Long countByUserId(Long userId);

    Long countByPostId(Long postId);

    Like1 findByUserIdAndPostId(Long userId, Long PostId);

    // don't need modifying because query method is annotated with, if custom query, then need
    @Transactional
    void deleteByUserIdAndPostId(Long userId, Long PostId);


}
