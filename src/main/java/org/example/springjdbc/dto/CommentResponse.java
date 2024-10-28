package org.example.springjdbc.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    Long id;
    AccountResponse account;
    String name_user;
    Long post_id;
    String content;
    LocalDateTime created_at;
    Long id_parent;
    String image;
    List<CommentResponse> replyComment;
}
