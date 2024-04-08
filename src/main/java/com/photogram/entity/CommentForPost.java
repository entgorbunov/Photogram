package com.photogram.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentForPost {
    private Long id;
    private Post post;
    private User user;
    private String text;
    private Timestamp commentTime;
}
