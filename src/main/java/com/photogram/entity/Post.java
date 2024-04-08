package com.photogram.entity;

import lombok.*;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private Long id;
    private User user;
    private String caption;
    private Timestamp postTime;
    private String imageUrl;

    public Post(Long id) {
        this.id = id;
    }
}
