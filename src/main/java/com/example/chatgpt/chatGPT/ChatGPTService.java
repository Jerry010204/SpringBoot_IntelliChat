package com.example.chatgpt.chatGPT;

import com.example.chatgpt.chat.Chat;
import com.example.chatgpt.chat.ChatService;
import com.example.chatgpt.post.PostRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ChatGPTService {

    final private PostRepository postRepository;
    final private ChatService chatService;

    @Value("${chatgpt.api-key}")
    private String apiKey;

    @Autowired
    public ChatGPTService(PostRepository postRepository, ChatService chatService) {
        this.postRepository = postRepository;
        this.chatService = chatService;
    }

    public ChatGptResponse chat(String token, GptRequest.Message newMessage){

        ObjectMapper objectMapper = new ObjectMapper();
        List<Chat> previousChats =  chatService.getChatsForUserWithinLast5Minutes(token);

        List<GptRequest.Message> messages
                = previousChats.stream()
                .flatMap(chat -> Stream.of(
                        new GptRequest.Message("user", chat.getQuestion()),
                        new GptRequest.Message("assistant", chat.getAnswer())))
                .collect(Collectors.toList());
        messages.add(newMessage);
        GptRequest gptRequest = GptRequest
                                    .builder()
                                    .model("gpt-3.5-turbo")
                                    .messages(messages)
                                    .temperature(0.7)
                                    .build();
        String input = null;
        try {
            input = objectMapper.writeValueAsString(gptRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("input:");
        System.out.println(HttpRequest.BodyPublishers.ofString(input));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(input))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ChatGptResponse chatGptResponse = null;


        if (response.statusCode() == 200) {
            try {
                chatGptResponse = objectMapper.readValue(response.body(), ChatGptResponse.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            String answer = chatGptResponse.choices()[chatGptResponse.choices().length-1].message().content();
            if(!answer.isEmpty()) {
                answer = answer.replace("\n", "").trim();
            }
        } else {
            System.out.println(response.statusCode());
            System.out.println(response.body());
        }
        return chatGptResponse;
    }
}
