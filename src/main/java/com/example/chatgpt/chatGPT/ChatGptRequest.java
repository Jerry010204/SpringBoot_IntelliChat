package com.example.chatgpt.chatGPT;



import java.util.List;

public record ChatGptRequest(String model, List<Message> messages, double temperature) {
    public record Message(String role, String content) {}

}