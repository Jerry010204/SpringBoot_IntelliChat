package com.example.chatgpt.view;



import com.example.chatgpt.like.LikeId;
import com.example.chatgpt.post.Post;
import com.example.chatgpt.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Builder
@AllArgsConstructor
public class View1 {

    @EmbeddedId
    private ViewId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime createdAt;

    public View1() {
    }
}
