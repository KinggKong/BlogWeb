package org.example.springjdbc.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.entity.Comment;
import org.example.springjdbc.rowmapper.CommentRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentRepository {
    JdbcTemplate jdbcTemplate;

    public List<Comment> findAll() {
        String sql = "select * from comment cmt \n" +
                "inner join account ac on ac.id = cmt.user_id ";
        return jdbcTemplate.query(sql, new CommentRowMapper());
    }

    public List<Comment> findByAccountIdAndPostId(int accountId, int postId) {
        String sql = "select * from comment where user_id = ? and post_id = ?";
        return jdbcTemplate.query(sql, new CommentRowMapper(), accountId, postId);
    }

    public List<Comment> findByPostId(Long postId) {
        String sql = "select * from comment cmt\n" +
                "                inner join account ac on ac.id = cmt.user_id where post_id = ? and (id_parent is null or id_parent = 0) order by cmt.created_at desc";
        return jdbcTemplate.query(sql, new CommentRowMapper(), postId);
    }

    public int insertComment(Comment comment) {
        String sql = "insert into comment(user_id,name_user,post_id,content,id_parent,image) values(?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, comment.getAccount().getId(), comment.getName_user(), comment.getPost_id(), comment.getContent(), comment.getId_parent(), comment.getImage());
    }

    public List<Comment> findChildrenComment(Long commentId) {
        String sql = "select * from comment cmt\n" +
                "inner join account ac on ac.id = cmt.user_id  where id_parent = ? ";
        return jdbcTemplate.query(sql, new CommentRowMapper(), commentId);
    }


}
