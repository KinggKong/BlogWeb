package org.example.springjdbc.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenRequest {
    String username;
    String password;
}
