package com.example.sce.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampGenerator {

    public static String generateTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
