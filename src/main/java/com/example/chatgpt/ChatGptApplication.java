package com.example.chatgpt;

import com.example.chatgpt.post.PostController;
import com.example.chatgpt.post.PostRepository;
import com.example.chatgpt.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChatGptApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatGptApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, PostRepository postRepository, PostController postController){
        return args -> {

//            User KaLok = User
//                    .builder()
//                    .id(1l)
//                    .email("123")
//                    .password("123")
//                    .role(Role.USER)
//                    .build();
//            userRepository.save(KaLok);
//            System.out.println(postRepository.findAll());


        };
    }

}
