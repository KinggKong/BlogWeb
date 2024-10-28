package org.example.springjdbc.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.entity.LikePost;
import org.example.springjdbc.rowmapper.LikePostRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LikePostRepository {
    JdbcTemplate jdbcTemplate;

    public int likePost(Long idPost, Long idAccount) {
        String sql = " insert into like_post(id_account,id_post) values(?,?)";
        return jdbcTemplate.update(sql, idAccount, idPost);
    }

    public int dislikePost(Long idPost, Long idAccount) {
        String sql = " delete from like_post where id_post=? and id_account=?";
        return jdbcTemplate.update(sql, idPost, idAccount);
    }

    public boolean checkLikedPost(Long postId, Long userId) {
        String sql = "SELECT COUNT(*) FROM like_post WHERE id_post = ? AND id_account = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, postId, userId);
        return count != null && count > 0;
    }

    public Long countLikeByPostID(Long postId) {
        String sql = "select count(*) from like_post where id_post = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, postId);
    }
}
