package com.photogram.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class Comment {
    private Long id;
    private Long postId;
    private Long userId;
    private String text;
    private Timestamp commentTime;
}
