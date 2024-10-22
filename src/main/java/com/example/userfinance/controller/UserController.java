package com.example.userfinance.controller;

import com.example.userfinance.dto.request.LoginRequestDto;
import com.example.userfinance.dto.request.RegisterRequestDto;
import com.example.userfinance.dto.response.BaseResponseDto;
import com.example.userfinance.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        BaseResponseDto responseDto = authService.login(loginRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
        BaseResponseDto responseDto = authService.register(registerRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}
