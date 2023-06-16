package com.example.chatgpt.chat;

import com.example.chatgpt.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {


    List<Chat> findAll();

    Long countByUserId(Long userId);

    @Query(value = "SELECT c FROM Chat c WHERE c.user.id = ?1 ORDER BY c.createdAt DESC")
    List<Chat> findByUserId(Long userId);


    @Query(value = "SELECT c FROM Chat c WHERE c.user.id = ?1 ORDER BY c.createdAt ASC")
    List<Chat> findByUserIdAcs(Long userId);

    Chat findChatById(Long chatId);

    @Query(value = "SELECT c FROM Chat c WHERE c.user.id = ?1 AND c.createdAt > ?2")
    List<Chat> findByUserIdAndCreatedAtAfter(Long userId, LocalDateTime time);


}

