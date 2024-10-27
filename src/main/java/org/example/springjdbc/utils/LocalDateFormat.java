package org.example.springjdbc.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class LocalDateFormat {
    public static LocalDate toDate(LocalDateTime localDateTime) {
        LocalDate date = localDateTime.toLocalDate();
        return date;
    }
}
