package com.example.chatgpt.chatGPT;


import lombok.Builder;
import lombok.Data;

public record ChatGptResponse(
        String id,
        String object,
        int created,
        String model,
        ChatGptResponseChoice[] choices,
        ChatGptResponseUsage usage) {
}
