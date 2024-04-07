package com.photogram.entity;

import lombok.Data;

@Data
public class Image {
    private Long id;
    private String path;
    private Long postId;
    private Long userId;

}
