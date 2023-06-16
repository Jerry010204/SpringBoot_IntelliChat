package com.example.chatgpt.like;


import com.example.chatgpt.post.Post;
import com.example.chatgpt.post.PostService;
import com.example.chatgpt.user.User;
import com.example.chatgpt.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    private final UserService userService;

    private final PostService postService;
    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }


    public boolean isLikedByUser(Long postId, String token){
        User user = userService.getUserByJwt(token.substring(7));
        System.out.println(user +"user is liked");
        System.out.println(postId + "post id is liked");
        if (postService.getPostByPostId(postId).isPresent() && likeRepository.findByUserIdAndPostId(user.getId(), postId) != null)
            return true;
        return false;
    }

    public Like1 likeOrUnlikePost(Long postId, String token){
        User user = userService.getUserByJwt(token.substring(7));
        Optional<Post> post = postService.getPostByPostId(postId);


        if (user != null && post.isPresent()) {
            if (!isLikedByUser(postId, token)) {
                Like1 like = Like1.builder()
                        .id(new LikeId(user.getId(), postId))
                        .post(post.get())
                        .user(user)
                        .createdAt(LocalDateTime.now())
                        .build();
                return likeRepository.save(like);
            }
            else {
                 likeRepository.deleteByUserIdAndPostId(user.getId(),postId);
            }
        }
        return null;
    }

    public Long countUserLikesByJwt(String token){
        User user = userService.getUserByJwt(token.substring(7));
        Long userId = user.getId();
        return likeRepository.countByUserId(userId);
    }

    public Long countPostLikesByPostId(Long postId){
        return likeRepository.countByPostId(postId);
    }
}
