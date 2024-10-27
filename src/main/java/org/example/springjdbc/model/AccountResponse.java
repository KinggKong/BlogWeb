package org.example.springjdbc.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {
    Long id;
    String username;
    String password;
    Date dob;
    LocalDateTime created_at;
    LocalDateTime updated_at;
    String image;
    int status;
    List<CommentResponse> comments;
}
