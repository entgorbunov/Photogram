package com.photogram.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    private Long id;
    private String path;
    private Post postId;
    private User userId;
    private Timestamp uploadedTime;

    public Image(Long id) {
        this.id = id;
    }
}
