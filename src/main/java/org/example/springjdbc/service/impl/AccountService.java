package org.example.springjdbc.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.entity.Account;
import org.example.springjdbc.mapper.AccountMapper;
import org.example.springjdbc.dto.AccountResponse;
import org.example.springjdbc.repository.AccountRepository;
import org.example.springjdbc.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    AccountRepository accountRepository;
    CommentRepository commentRepository;
    CommentService commentService;

    public List<AccountResponse> findAll() {
        return null;
    }

    public AccountResponse findById(Long id) {
        Account account = accountRepository.findByID(id);
        return AccountMapper.toAccountResponse(account);
    }

    public List<AccountResponse> findByPostId(Long id) {
        List<Account> accounts = accountRepository.findByIDPost(id);

        List<AccountResponse> accountResponses = AccountMapper.toAccountResponseList(accounts);
        for (AccountResponse accountResponse : accountResponses) {
            accountResponse.setComments(commentService.findByPostId(id));
        }
        return accountResponses;
    }

    public AccountResponse findByUserName(String username) {
        Account account = accountRepository.findByUserName(username);
        return AccountMapper.toAccountResponse(account);
    }
}
