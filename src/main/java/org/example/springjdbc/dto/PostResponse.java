package org.example.springjdbc.dto;

import lombok.*;

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
    CategoryResponse categoryResponse;
    List<CommentResponse> comments;
    Long totalLikes;
}
