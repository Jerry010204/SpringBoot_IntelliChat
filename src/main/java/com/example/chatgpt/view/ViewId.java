package com.example.chatgpt.view;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
@Embeddable
public class ViewId implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;

    public ViewId(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
