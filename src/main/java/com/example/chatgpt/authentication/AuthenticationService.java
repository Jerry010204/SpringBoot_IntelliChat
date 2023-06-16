package com.example.chatgpt.authentication;

import com.example.chatgpt.exception.EmailAlreadyExistsException;
import com.example.chatgpt.exception.UsernameAlreadyExistsException;
import com.example.chatgpt.security.JwtService;
import com.example.chatgpt.security.PasswordGenerator;
import com.example.chatgpt.user.Role;
import com.example.chatgpt.user.User;
import com.example.chatgpt.user.UserRepository;
import com.example.chatgpt.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoders;
    private final UserService userService;


    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .name(request.getName())
                .email(request.getEmail())
                .imageUrl(request.getImageUrl())
                .password(passwordEncoders.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        try {
            if (repository.findUserByUsername(user.getUsername()).isPresent()) {
                throw new UsernameAlreadyExistsException("Username already exists: " + user.getUsername());
            }
            if (repository.findUserByEmail(user.getEmail()).isPresent()) {
                throw new EmailAlreadyExistsException("Email already exists: " + user.getEmail());
            }
            repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException e) {
            return AuthenticationResponse.builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        Optional<User> user = userService.findUserByUsername(request.getUsername());


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        String jwtToken = jwtService.generateToken(user.get());
        var refreshToken = jwtService.generateRefreshToken(user.get());

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refreshToken(
            HttpServletRequest request
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return AuthenticationResponse.builder().error("Invalid refresh token").build();
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.repository.findUserByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                return authResponse;
            }
        }
        return AuthenticationResponse.builder().error("Invalid refresh token").build();
    }
}
