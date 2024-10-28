package org.example.springjdbc.rowmapper;

import org.example.springjdbc.entity.LikePost;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikePostRowMapper implements RowMapper<LikePost> {
    @Override
    public LikePost mapRow(ResultSet rs, int rowNum) throws SQLException {
        return LikePost.builder()
                .id(rs.getLong("id"))
                .postId(rs.getLong("id_post"))
                .userId(rs.getLong("id_account"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .build();
    }
}
