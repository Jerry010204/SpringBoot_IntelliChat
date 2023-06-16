package com.example.chatgpt.authentication;

import com.example.chatgpt.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private String name;
    private String email;
    private String imageUrl;
    private String password;
    private Role role;

}

