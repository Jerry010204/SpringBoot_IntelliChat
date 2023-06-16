package com.example.chatgpt.view;

import com.example.chatgpt.post.Post;
import com.example.chatgpt.post.PostService;
import com.example.chatgpt.user.User;
import com.example.chatgpt.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class ViewService {

    private final ViewRepository viewRepository;

    private final UserService userService;

    private final PostService postService;
    public ViewService(ViewRepository viewRepository, UserService userService, PostService postService) {
        this.viewRepository = viewRepository;
        this.userService = userService;
        this.postService = postService;
    }


    public boolean isViewedByUser(Long postId, String token){
        User user = userService.getUserByJwt(token.substring(7));
        if (postService.getPostByPostId(postId).isPresent() && viewRepository.findByUserIdAndPostId(user.getId(), postId) != null)
            return true;
        return false;
    }

    public View1 viewPost(Long postId, String token){
        User user = userService.getUserByJwt(token.substring(7));
        Optional<Post> post = postService.getPostByPostId(postId);

        if (user != null && post.isPresent()) {
            if (!isViewedByUser(postId, token)) {
                View1 view = View1.builder()
                        .id(new ViewId(user.getId(), postId))
                        .post(post.get())
                        .user(user)
                        .createdAt(LocalDateTime.now())
                        .build();
                return viewRepository.save(view);
            }
            else {
                return null;
            }
        }
        return null;
    }

    public Long countUserViewsByJwt(String token){
        User user = userService.getUserByJwt(token.substring(7));
        Long userId = user.getId();
        return viewRepository.countByUserId(userId);
    }

    public Long countPostViewsByPostId(Long postId){
        return viewRepository.countByPostId(postId);
    }
}