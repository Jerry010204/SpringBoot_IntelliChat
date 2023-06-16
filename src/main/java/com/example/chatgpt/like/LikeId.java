package com.example.chatgpt.like;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
@Embeddable
public class LikeId implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;

    public LikeId(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
