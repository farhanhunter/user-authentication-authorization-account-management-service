package com.example.userfinance.service;

import com.example.userfinance.dto.request.LoginRequestDto;
import com.example.userfinance.dto.request.RegisterRequestDto;
import com.example.userfinance.dto.response.BaseResponseDto;

public interface AuthService {
    BaseResponseDto login(LoginRequestDto loginRequestDto);
    BaseResponseDto register(RegisterRequestDto registerRequestDto);
}
