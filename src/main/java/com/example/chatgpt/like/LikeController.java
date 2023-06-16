package com.example.chatgpt.like;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/likes")
@CrossOrigin
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("post/{postId}/count")
    public Long getPostTotalLikes(@PathVariable("postId") Long postId){
        return likeService.countPostLikesByPostId(postId);
    }

    @GetMapping("me/count")
    public Long getUserTotalLikes(@RequestHeader("Authorization") String token){
        return likeService.countUserLikesByJwt(token);
    }

    @GetMapping("/{postId}/isLiked")
    public boolean isLikedByUser(@PathVariable("postId") Long postId, @RequestHeader("Authorization") String token) {
        return likeService.isLikedByUser(postId, token);
    }

    @PostMapping
    public Like1 likeOrUnlikePost(@RequestBody Map<String, Long> postId, @RequestHeader("Authorization") String token){
        return likeService.likeOrUnlikePost(postId.get("postId"), token);
    }


}
