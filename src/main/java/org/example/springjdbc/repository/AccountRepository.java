package org.example.springjdbc.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.entity.Account;
import org.example.springjdbc.mapper.AccountMapper;
import org.example.springjdbc.rowmapper.AccountRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountRepository {
    JdbcTemplate jdbcTemplate;

    public List<Account> findAll() {
        String sql = "select * from account";
        return jdbcTemplate.query(sql, new AccountRowMapper());
    }

    public List<Account> findByIDPost(Long idPost) {
        String sql = "select ac.* from account ac\n" +
                "inner join comment cmt on cmt.user_id = ac.id and post_id = ?";
        return jdbcTemplate.query(sql, new AccountRowMapper(), idPost);
    }

    public Account findByID(Long id) {
        String sql = "select * from account where id = ?";
        return jdbcTemplate.queryForObject(sql, new AccountRowMapper(), id);
    }

    public Account findByUserName(String username) {
        String sql = "select * from account where username = ?";
        return jdbcTemplate.queryForObject(sql, new AccountRowMapper(), username);
    }
}
