package com.photogram.entity;

import lombok.Data;

@Data
public class Image {
    private int id;
    private String path;
    private int postId;
    private int userId;

}
