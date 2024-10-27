package org.example.springjdbc.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
    Long id;
    String title;
    String content;
    String image;
    String name_user;
    LocalDateTime created_at;
    int status;
    Long totalComments;
    AccountResponse author;
    List<CommentResponse> comments;
}
