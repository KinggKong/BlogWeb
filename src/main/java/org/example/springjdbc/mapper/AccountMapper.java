package org.example.springjdbc.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.entity.Account;
import org.example.springjdbc.model.AccountResponse;
import org.example.springjdbc.model.CommentResponse;
import org.example.springjdbc.service.impl.AccountService;

import java.util.ArrayList;
import java.util.List;

public class AccountMapper {

    public static AccountResponse toAccountResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .username(account.getUsername())
                .password(account.getPassword())
                .dob(account.getDob())
                .created_at(account.getCreated_at())
                .updated_at(account.getUpdated_at())
                .image(account.getImage())
                .status(account.getStatus())
                .build();
    }

    public static List<AccountResponse> toAccountResponseList(List<Account> accounts) {
        List<AccountResponse> accountResponses = new ArrayList<>();
        for (Account account : accounts) {
            accountResponses.add(toAccountResponse(account));
        }
        return accountResponses;
    }


    public static Account toAccount(AccountResponse accountResponse) {
        return Account.builder()
                .id(accountResponse.getId())
                .username(accountResponse.getUsername())
                .password(accountResponse.getPassword())
                .dob(accountResponse.getDob())
                .created_at(accountResponse.getCreated_at())
                .updated_at(accountResponse.getUpdated_at())
                .image(accountResponse.getImage())
                .status(accountResponse.getStatus())
                .build();
    }
}
