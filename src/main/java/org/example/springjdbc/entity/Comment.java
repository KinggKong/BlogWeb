package org.example.springjdbc.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    Long id;
    Account account;
    String name_user;
    Long post_id;
    String content;
    LocalDateTime created_at;
    Long id_parent;
    int status;
    String image;
}
