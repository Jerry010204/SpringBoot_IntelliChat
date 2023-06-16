package com.example.chatgpt.chat;

import com.example.chatgpt.user.User;
import com.example.chatgpt.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    ChatRepository chatRepository;

    UserService userService;

    public ChatService(ChatRepository chatRepository, UserService userService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    public List<Chat> findChatsByJwt(String token){
        User user = userService.getUserByJwt(token.substring(7));
        Long userId = user.getId();
        return chatRepository.findByUserId(userId);
    }

    public List<Chat> findChatsByJwtAcs(String token){
        User user = userService.getUserByJwt(token.substring(7));
        Long userId = user.getId();
        return chatRepository.findByUserIdAcs(userId);
    }

    public Long countChatsByJwt(String token){
        User user = userService.getUserByJwt(token.substring(7));
        Long userId = user.getId();
        return chatRepository.countByUserId(userId);
    }

    public Chat findChatByChatId(Long chatId){
        return chatRepository.findChatById(chatId);
    }

    public List<Chat> getChatsForUserWithinLast5Minutes(String token) {
        User user = userService.getUserByJwt(token);
        LocalDateTime start = LocalDateTime.now().minusMinutes(5);
        return chatRepository.findByUserIdAndCreatedAtAfter(user.getId(), start);
    }

    public Chat saveNewChat(String token, Chat chat){
        User user = userService.getUserByJwt(token);
        chat.setUser(user);
        chat.setCreatedAt(LocalDateTime.now());
        return chatRepository.save(chat);
    }



}
