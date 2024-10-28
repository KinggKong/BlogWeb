package org.example.springjdbc.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryService {
    CategoryRepository categoryRepository;

    public Long countPostByCategoryId(Long categoryId) {
        return categoryRepository.countPostByCategory(categoryId);
    }
}
