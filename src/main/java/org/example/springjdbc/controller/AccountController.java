package org.example.springjdbc.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.model.AccountResponse;
import org.example.springjdbc.service.impl.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/accounts")
public class AccountController {
    AccountService accountService;

    @GetMapping("")
    public List<AccountResponse> findAll() {
        return accountService.findByPostId(1L);
    }
}
