package com.example.userfinance.controller;

import com.example.userfinance.dto.request.AccountRequestDto;
import com.example.userfinance.dto.response.BaseResponseDto;
import com.example.userfinance.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponseDto> createAccount(@RequestParam Long userId, @Valid @RequestBody AccountRequestDto accountRequestDto) {
        BaseResponseDto baseResponseDto = accountService.createAccount(userId, accountRequestDto);
        return ResponseEntity.status(baseResponseDto.getStatus()).body(baseResponseDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<BaseResponseDto> getAccountsByUserId(@PathVariable Long userId) {
        BaseResponseDto baseResponseDto = accountService.getAccountByUserId(userId);
        return ResponseEntity.status(baseResponseDto.getStatus()).body(baseResponseDto);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<BaseResponseDto> updateAccount(@PathVariable Long accountId, @Valid @RequestBody AccountRequestDto accountRequestDto) {
        BaseResponseDto baseResponseDto = accountService.updateAccount(accountId, accountRequestDto);
        return ResponseEntity.status(baseResponseDto.getStatus()).body(baseResponseDto);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<BaseResponseDto> deleteAccount(@PathVariable Long accountId) {
        BaseResponseDto baseResponseDto = accountService.deleteAccount(accountId);
        return ResponseEntity.status(baseResponseDto.getStatus()).body(baseResponseDto);
    }
}
