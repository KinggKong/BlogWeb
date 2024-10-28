package org.example.springjdbc.model;

import lombok.*;
import org.example.springjdbc.entity.Account;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {
    Account account;
    String name_user;
    Long post_id;
    String content;
    LocalDateTime created_at;
    Long id_parent;
    String image;
    String icon;
}
