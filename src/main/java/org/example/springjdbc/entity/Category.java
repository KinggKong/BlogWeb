package org.example.springjdbc.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    Long id;
    String category_name;
}
