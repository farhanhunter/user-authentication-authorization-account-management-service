package com.example.userfinance.service;

import com.example.userfinance.dto.request.AccountRequestDto;
import com.example.userfinance.dto.response.BaseResponseDto;

public interface AccountService {
    BaseResponseDto createAccount(Long userId, AccountRequestDto accountRequestDto);

    BaseResponseDto getAccountByUserId(Long userId);

    BaseResponseDto updateAccount(Long accountId, AccountRequestDto accountRequestDto);

    BaseResponseDto deleteAccount(Long accountId);
}
