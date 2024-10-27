package org.example.springjdbc.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    Long id;
    String username;
    String password;
    Date dob;
    LocalDateTime created_at;
    LocalDateTime updated_at;
    String image;
    int status;
}
