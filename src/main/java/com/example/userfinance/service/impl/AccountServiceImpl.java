package com.example.userfinance.service.impl;

import com.example.userfinance.dto.request.AccountRequestDto;
import com.example.userfinance.dto.response.BaseResponseDto;
import com.example.userfinance.entity.Account;
import com.example.userfinance.entity.User;
import com.example.userfinance.repository.AccountRepository;
import com.example.userfinance.repository.UserRepository;
import com.example.userfinance.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public BaseResponseDto createAccount(Long userId, AccountRequestDto accountRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account newAccount = new Account();
        newAccount.setAccountType(accountRequestDto.getAccountType());
        newAccount.setAccountNumber(accountRequestDto.getAccountNumber());
        newAccount.setBalance(accountRequestDto.getBalance());
        newAccount.setUser(user);

        Account savedAccount = accountRepository.save(newAccount);

        return BaseResponseDto.builder()
                .status(HttpStatus.CREATED)
                .description("Account created successfully")
                .data(Map.of(
                        "accountId", savedAccount.getId(),
                        "accountType", savedAccount.getAccountType(),
                        "accountNumber", savedAccount.getAccountNumber(),
                        "balance", savedAccount.getBalance()
                ))
                .build();
    }

    @Override
    public BaseResponseDto getAccountByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Account> accounts = accountRepository.findByUser(user);

        List<Map<String, Object>> accountData = accounts.stream()
                .map(account -> {
                    Map<String, Object> accountMap = new HashMap<>();
                    accountMap.put("accountId", account.getId());
                    accountMap.put("accountType", account.getAccountType());
                    accountMap.put("accountNumber", account.getAccountNumber());
                    accountMap.put("balance", account.getBalance());
                    return accountMap;
                })
                .toList();

        return BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .description("Accounts retrieved successfully")
                .data(Map.of("accounts", accountData))
                .build();
    }

    @Override
    public BaseResponseDto updateAccount(Long accountId, AccountRequestDto accountRequestDto) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setAccountType(accountRequestDto.getAccountType());
        account.setAccountNumber(accountRequestDto.getAccountNumber());
        account.setBalance(accountRequestDto.getBalance());

        Account updatedAccount = accountRepository.save(account);

        return BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .description("Account updated successfully")
                .data(Map.of(
                        "accountId", updatedAccount.getId(),
                        "accountType", updatedAccount.getAccountType(),
                        "accountNumber", updatedAccount.getAccountNumber(),
                        "balance", updatedAccount.getBalance()
                ))
                .build();
    }

    @Override
    public BaseResponseDto deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountRepository.delete(account);

        return BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .description("Account deleted successfully")
                .build();
    }
}
