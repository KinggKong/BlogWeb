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
        String sql = "select p.*,ac.*,cate.id as idCategory,cate.category_name from post p \n" +
                "\tinner join account ac on ac.id = p.author\n" +
                "\tinner join category cate on cate.id = p.id_category\n" +
                "    order by p.created_at desc limit ? offset ?";
        return jdbcTemplate.query(sql, new PostRowMapper(), pageSize, offset);
    }

    public List<Post> search(String keyword, int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        String sql = "select p.*,ac.*,cate.id as idCategory,cate.category_name from post p \n" +
                "inner join account ac on ac.id = p.author inner join category cate on cate.id = p.id_category where p.title like ?  order by p.created_at desc limit ? offset ?";
        return jdbcTemplate.query(sql, new PostRowMapper(), "%" + keyword + "%", pageSize, offset);
    }

    public List<Post> findByCategory(Long categoryID, int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        String sql = "select p.*,ac.*,cate.id as idCategory,cate.category_name from post p \n" +
                "inner join account ac on ac.id = p.author inner join category cate on cate.id = p.id_category where p.id_category = ?  order by p.created_at desc limit ? offset ?";
        return jdbcTemplate.query(sql, new PostRowMapper(), categoryID, pageSize, offset);
    }

    public List<Post> findByMyFavorite(Long idAccount, int pageNumber, int pageSize) {
        String sql = "select p.*,ac.*,cate.id as idCategory,cate.category_name from post p \n" +
                "inner join account ac on ac.id = p.author inner join category cate on cate.id = p.id_category \n" +
                "inner join like_post lp on p.id = lp.id_post where lp.id_account = ?\n" +
                "  order by p.created_at desc limit ? offset ?";
        return jdbcTemplate.query(sql, new PostRowMapper(), idAccount, pageSize, pageNumber);
    }

    public Long countMyFavorite(Long idAccount) {
        String sql = "  select count(*) from post p inner join account ac on ac.id = p.author inner join category cate on cate.id = p.id_category inner join like_post lp on p.id = lp.id_post where lp.id_account = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, idAccount);
    }

    public List<Post> top5Recent() {
        String sql = "  select p.*,ac.*,cate.id as idCategory,cate.category_name from post p \n" +
                "\tinner join account ac on ac.id = p.author\n" +
                "\tinner join category cate on cate.id = p.id_category order by p.created_at desc limit 5 ";
        return jdbcTemplate.query(sql, new PostRowMapper());
    }


    public Long countSearch(String keyword) {
        String sql = "select count(*) from post p \n" +
                "inner join account ac on ac.id = p.author where p.title like ?  ";
        return jdbcTemplate.queryForObject(sql, Long.class, "%" + keyword + "%");
    }

    public Post findById(Long id) {
        String sql = "select p.*,ac.*,cate.id as idCategory,cate.category_name from post p \n" +
                "inner join account ac on ac.id = p.author inner join category cate on cate.id = p.id_category where p.id = ?";
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


    public Long countByIdCategory(Long id) {
        String sql = "select count(*) from post where id_category = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, id);
    }
}
