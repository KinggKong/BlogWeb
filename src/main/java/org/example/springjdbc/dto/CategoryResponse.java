package org.example.springjdbc.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    Long id;
    String category_name;
}
