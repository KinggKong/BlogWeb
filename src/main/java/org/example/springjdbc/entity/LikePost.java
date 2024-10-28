package org.example.springjdbc.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikePost {
    Long id;
    Long postId;
    Long userId;
    LocalDateTime createdAt;
}
