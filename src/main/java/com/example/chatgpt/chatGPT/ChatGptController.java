package com.example.chatgpt.chatGPT;

import com.example.chatgpt.chat.Chat;
import com.example.chatgpt.chat.ChatRepository;
import com.example.chatgpt.user.User;
import com.example.chatgpt.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/chatgpt")
@CrossOrigin
public class ChatGptController {
    ChatGPTService chatGPTService;
    UserService userService;
    ChatRepository chatRepository;
    @Autowired
    public ChatGptController(ChatGPTService chatGPTService, UserService userService, ChatRepository chatRepository) {
        this.chatGPTService = chatGPTService;
        this.userService = userService;
        this.chatRepository = chatRepository;
    }

    @PostMapping( value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ChatGptResponse chatGpt(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> request) {
        token = token.substring(7);
        GptRequest.Message newMessage = new GptRequest.Message("user", request.get("content"));
        ChatGptResponse chatGptResponse = chatGPTService.chat(token, newMessage);
        if (!chatGptResponse.choices()[chatGptResponse.choices().length-1].message().content().isEmpty()) {
            System.out.println(token);
            User user = userService.getUserByJwt(token);
            Chat chat = new Chat().builder()
                    .question(request.get("content"))
                    .answer(chatGptResponse.choices()[chatGptResponse.choices().length - 1].message().content())
                    .user(user)
                    .createdAt(LocalDateTime.now())
                    .build();

            chatRepository.save(chat);
        }
        return chatGptResponse;
    }
}