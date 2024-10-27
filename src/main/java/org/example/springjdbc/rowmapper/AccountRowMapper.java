package org.example.springjdbc.rowmapper;

import org.example.springjdbc.entity.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Account.builder()
                .dob(rs.getDate("DOB"))
                .created_at(rs.getTimestamp("created_at").toLocalDateTime())
                .id(rs.getLong("id"))
                .password(rs.getString("password"))
                .username(rs.getString("username"))
                .image(rs.getString("image"))
                .status(rs.getInt("status"))
                .build();
    }
}
