package com.example.chatgpt.chat;


import com.example.chatgpt.user.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
@CrossOrigin
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/me")
    public List<Chat> findChatsByJwt(@RequestParam(value = "order", defaultValue = "") String order,@RequestHeader("Authorization") String token){
        if (order.equals("ASC")){
            return chatService.findChatsByJwtAcs(token);
        }
        return chatService.findChatsByJwt(token);
    }

    @GetMapping("/me/count")
    public Long countChatsByJwt(@RequestHeader("Authorization") String token){
        return chatService.countChatsByJwt(token);
    }

    @GetMapping("/me/5mins")
    public List<Chat> findChatsByJwtWithin5Mins(@RequestHeader("Authorization") String token){
        return chatService.getChatsForUserWithinLast5Minutes(token.substring(7));
    }

    @PostMapping("/me")
    public Chat createNewChat(@RequestHeader("Authorization") String token, @RequestBody Chat chat){
        return chatService.saveNewChat(token.substring(7), chat);
    }


}

