package com.example.chatgpt.view;


import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/views")
@CrossOrigin
public class ViewController {

    private final ViewService viewService;

    public ViewController(ViewService viewService) {
        this.viewService = viewService;
    }

    @GetMapping("post/{postId}/count")
    public Long getPostTotalViews(@PathVariable("postId") Long postId){
        return viewService.countPostViewsByPostId(postId);
    }

    @GetMapping("me/count")
    public Long getUserTotalViews(@RequestHeader("Authorization") String token){
        return viewService.countUserViewsByJwt(token);
    }

    @GetMapping("/{postId}/isViewed")
    public boolean isViewedByUser(@PathVariable("postId") Long postId, @RequestHeader("Authorization") String token) {
        return viewService.isViewedByUser(postId, token);
    }

    @PostMapping
    public View1 viewPost(@RequestBody Map<String, Long> postId, @RequestHeader("Authorization") String token){
        return viewService.viewPost(postId.get("postId"), token);
    }


}