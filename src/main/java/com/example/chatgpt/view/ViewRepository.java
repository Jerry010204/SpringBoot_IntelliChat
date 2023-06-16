package com.example.chatgpt.view;

import com.example.chatgpt.like.Like1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ViewRepository extends JpaRepository<View1, Long> {

    Long countByUserId(Long userId);

    Long countByPostId(Long postId);

    View1 findByUserIdAndPostId(Long userId, Long PostId);


}
