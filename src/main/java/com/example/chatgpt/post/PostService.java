package com.example.chatgpt.post;

import com.example.chatgpt.chat.Chat;
import com.example.chatgpt.chat.ChatService;
import com.example.chatgpt.user.User;
import com.example.chatgpt.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PostService {

    final private PostRepository postRepository;

    final private ChatService chatService;

    final private UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, ChatService chatService, UserService userService) {
        this.postRepository = postRepository;
        this.chatService = chatService;
        this.userService = userService;

    }

    public List<Post> getAllPostsByDate(){
        return postRepository.findAll();
    }

    // TODO: can be optimized ...
    public List<Post> getAllPostsByLikeAndView(){
        Map<Post, Long> postWithScore = new HashMap<>();
        List<Object[]> postsWithLike = postRepository.findAllWithLikeCount();
        List<Object[]> postsWithView = postRepository.findAllWithViewCount();

        List<Post> posts = new ArrayList<>();

        for (int i = 0; i < postsWithLike.size(); i++) {
            posts.add((Post)postsWithLike.get(i)[0]);
            Long likesCount = (Long) postsWithLike.get(i)[1];
            Long viewsCount =  (Long) postsWithView.get(i)[1];
            Long score = likesCount*5 + viewsCount;
            postWithScore.put((Post)postsWithLike.get(i)[0], score);
        }
        Collections.sort(posts, (post1, post2) -> Long.compare(postWithScore.get(post2), postWithScore.get(post1)));
        return posts;
    }


    public List<Post> getPostsByJwt(String token){
        User user = userService.getUserByJwt(token.substring(7));
        return postRepository.selectPostsByUserId(user.getId());
    }

    public Optional<Post> getPostByPostId(Long postId){
        return postRepository.findById(postId);
    }
    public Post createPost(Long chatId, String token){
        User user = userService.getUserByJwt(token.substring(7));
        Chat chat = chatService.findChatByChatId(chatId);
        System.out.println(chat);
        System.out.println(user);
        if (user.getUsername().equals(chat.getUser().getUsername())) {
            Post post = Post.builder()
                            .chat(chat)
                            .createdAt(LocalDateTime.now())
                            .build();
            return postRepository.save(post);
        }
        return null;
    }

}
