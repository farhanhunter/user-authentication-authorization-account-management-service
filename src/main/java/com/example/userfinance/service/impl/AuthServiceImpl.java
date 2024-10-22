package com.example.userfinance.service.impl;

import com.example.userfinance.dto.request.LoginRequestDto;
import com.example.userfinance.dto.request.RegisterRequestDto;
import com.example.userfinance.dto.response.BaseResponseDto;
import com.example.userfinance.entity.User;
import com.example.userfinance.repository.UserRepository;
import com.example.userfinance.security.CustomUserDetails;
import com.example.userfinance.service.AuthService;
import com.example.userfinance.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomUserDetails(user.get());
    }

    @Override
    public BaseResponseDto login(LoginRequestDto loginRequestDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
            );

            Optional<User> userOptional = userRepository.findByUsername(loginRequestDto.getUsername());
            if (userOptional.isEmpty()) {
                return BaseResponseDto.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .description("User not found")
                        .build();
            }

            String token = jwtUtil.generateToken(loginRequestDto.getUsername());

            return BaseResponseDto.builder()
                    .status(HttpStatus.OK)
                    .description("Login successful")
                    .data(Map.of("token", token))
                    .build();
        } catch (AuthenticationException e) {
            return BaseResponseDto.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .description("Invalid username or password")
                    .build();
        }
    }


    @Override
    public BaseResponseDto register(RegisterRequestDto registerRequestDto) {
        if (userRepository.findByUsername(registerRequestDto.getUsername()).isPresent()) {
            return BaseResponseDto.builder()
                    .status(HttpStatus.CONFLICT)
                    .description("Username already exists")
                    .build();
        }

        if (userRepository.findByEmail(registerRequestDto.getEmail()).isPresent()) {
            return BaseResponseDto.builder()
                    .status(HttpStatus.CONFLICT)
                    .description("Email already exists")
                    .build();
        }

        User newUser = new User();
        newUser.setUsername(registerRequestDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        newUser.setEmail(registerRequestDto.getEmail());
        newUser.setRole("USER");

        userRepository.save(newUser);

        String token = jwtUtil.generateToken(registerRequestDto.getUsername());

        return BaseResponseDto.builder()
                .status(HttpStatus.CREATED)
                .description("User registered successfully")
                .data(Map.of("token", token))
                .build();
    }

}
