package org.example.springjdbc.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    int age;
    String department;
    String email;
    Date jonDate;
    double salary;
    String username;
}
