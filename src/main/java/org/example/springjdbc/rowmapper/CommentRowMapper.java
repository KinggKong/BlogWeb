package org.example.springjdbc.rowmapper;

import org.example.springjdbc.entity.Account;
import org.example.springjdbc.entity.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentRowMapper implements RowMapper<Comment> {

    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = Account.builder()
                .id(rs.getLong(10))
                .username(rs.getString(11))
                .password(rs.getString(12))
                .dob(rs.getDate(13))
                .created_at(rs.getTimestamp(14).toLocalDateTime())
                .updated_at(rs.getTimestamp(15).toLocalDateTime())
                .status(rs.getInt(16))
                .image(rs.getString(17))
                .build();
        return Comment.builder()
                .id(rs.getLong(1))
                .account(account)
                .name_user(rs.getString(3))
                .post_id(rs.getLong(4))
                .content(rs.getString(5))
                .created_at(rs.getTimestamp(6).toLocalDateTime())
                .id_parent(rs.getLong(7))
                .status(rs.getInt(8))
                .image(rs.getString(9))
                .build();
    }

}
