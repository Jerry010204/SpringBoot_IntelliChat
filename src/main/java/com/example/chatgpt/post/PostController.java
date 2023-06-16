package com.example.chatgpt.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
@CrossOrigin
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public List<Post> getAllPosts(@RequestParam(value = "orderBy", defaultValue = "date") String orderByField){
        System.out.println(orderByField);
        if (orderByField.equals("LikeAndView")){
            System.out.println("view");
            return postService.getAllPostsByLikeAndView();
        }
        return postService.getAllPostsByDate();
    }

    @GetMapping("/me")
    public List<Post> getPostsByJwt(@RequestHeader("Authorization") String token){
        return postService.getPostsByJwt(token);
    }

    @PostMapping("/me")
    public Post createPost(@RequestBody Map<String, Long> chatId, @RequestHeader("Authorization") String token){
        return postService.createPost(chatId.get("chatId"), token);
    }


}