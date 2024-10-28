package org.example.springjdbc.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryRepository {
    JdbcTemplate jdbcTemplate;

    public Long countPostByCategory(Long idCategory) {
        String sql = "select count(*) from post where id_category=?";
        return jdbcTemplate.queryForObject(sql, Long.class, idCategory);
    }
}
