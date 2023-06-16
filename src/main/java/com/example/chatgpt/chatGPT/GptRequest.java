package com.example.chatgpt.chatGPT;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GptRequest {

    String model;
    List<Message> messages;
    double temperature;
    public record Message(String role, String content) {};


}
