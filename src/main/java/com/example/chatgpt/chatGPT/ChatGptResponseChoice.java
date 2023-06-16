package com.example.chatgpt.chatGPT;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatGptResponseChoice(
    int index,
    String finish_reason,
    ChatGptRequest.Message message
) {

}
