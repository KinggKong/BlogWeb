package org.example.springjdbc.mapper;

import org.example.springjdbc.dto.CategoryResponse;
import org.example.springjdbc.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {
    public static Category toCategory(CategoryResponse response) {
        return Category.builder()
                .category_name(response.getCategory_name())
                .id(response.getId())
                .build();
    }

    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .category_name(category.getCategory_name())
                .build();
    }

    public List<CategoryResponse> toListCategoryResponses(List<Category> categories) {
        List<CategoryResponse> categoriesResponse = new ArrayList<>();
        for (Category category : categories) {
            categoriesResponse.add(toCategoryResponse(category));
        }
        return categoriesResponse;
    }

}
