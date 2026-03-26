package com.lms.lms.service;

import com.lms.lms.dto.AuthResponse;
import com.lms.lms.dto.LoginRequest;
import com.lms.lms.dto.RegisterRequest;
import com.lms.lms.entity.User;
import com.lms.lms.entity.UserRole;
import com.lms.lms.repository.UserRepository;
import com.lms.lms.security.CustomUserDetailsService;
import com.lms.lms.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        String token = jwtUtils.generateToken(customUserDetailsService.loadUserByUsername(user.getEmail()));
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String token = jwtUtils.generateToken(customUserDetailsService.loadUserByUsername(request.getEmail()));
        return new AuthResponse(token);
    }
}
