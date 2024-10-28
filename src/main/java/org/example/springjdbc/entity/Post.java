package org.example.springjdbc.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    Long id;
    String title;
    String content;
    String image;
    String name_user;
    LocalDateTime created_at;
    int status;
    Account author;
    Category category;
}
