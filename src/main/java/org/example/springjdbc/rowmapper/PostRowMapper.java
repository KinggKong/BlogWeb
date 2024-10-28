package org.example.springjdbc.rowmapper;

import org.example.springjdbc.entity.Account;
import org.example.springjdbc.entity.Category;
import org.example.springjdbc.entity.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostRowMapper implements RowMapper<Post> {
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category = Category.builder()
                .id(rs.getLong("idCategory"))
                .category_name(rs.getString("category_name"))
                .build();
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
        return Post.builder()
                .id(rs.getLong(1))
                .title(rs.getString(7))
                .content(rs.getString(2))
                .name_user(rs.getString(3))
                .image(rs.getString(8))
                .created_at(rs.getTimestamp(4).toLocalDateTime())
                .status(rs.getInt(6))
                .author(account)
                .category(category)
                .build();
    }
}
