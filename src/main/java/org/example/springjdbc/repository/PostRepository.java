package org.example.springjdbc.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.entity.Post;
import org.example.springjdbc.rowmapper.PostRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostRepository {
    JdbcTemplate jdbcTemplate;

    public List<Post> findAll(int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        String sql = "select * from post p \n" +
                "inner join account ac on ac.id = p.author  order by p.created_at desc limit ? offset ?";
        return jdbcTemplate.query(sql, new PostRowMapper(), pageSize, offset);
    }

    public Post findById(Long id) {
        String sql = "select * from post p \n" +
                "inner join account ac on ac.id = p.author where p.id = ?";
        return jdbcTemplate.queryForObject(sql, new PostRowMapper(), id);
    }

    public Long countCommentByPost(Long postId) {
        String sql = "select count(*) from comment where post_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, postId);
    }

    public Long countAllPost() {
        String sql = "select count(*) from post";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

}
