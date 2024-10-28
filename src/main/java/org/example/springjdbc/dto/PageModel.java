package org.example.springjdbc.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PageModel<T> {
    @Builder.Default
    int currentPage =1;
    List<Integer> pageNumbers;
    int totalPage;
    List<T> data;
}
