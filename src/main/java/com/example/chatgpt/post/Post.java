package com.example.chatgpt.post;

import com.example.chatgpt.chat.Chat;
import com.example.chatgpt.like.Like1;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;

//    @JsonIgnore
    @OneToOne
    @JoinColumn(
            name = "chat_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "chat_id_fk"
            )
    )
    private Chat chat;


    public Post() {
    }



}