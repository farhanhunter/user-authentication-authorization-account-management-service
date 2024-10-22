package com.example.userfinance.repository;

import com.example.userfinance.entity.Account;
import com.example.userfinance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUser(User user);
}
