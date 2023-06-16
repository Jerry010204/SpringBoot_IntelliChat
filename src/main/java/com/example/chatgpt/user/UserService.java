package com.example.chatgpt.user;

import com.example.chatgpt.post.PostRepository;
import com.example.chatgpt.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private JwtService jwtService;
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByJwt(String token) {
        System.out.println(token+"from user service");
        String username = jwtService.extractUsername(token);
        Optional<User> user = userRepository.findUserByUsername(username);
        return user.orElse(null);
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            return userRepository.save(existingUser);
        } else {
            return null;
        }
    }

    public Optional<User> findUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }






}