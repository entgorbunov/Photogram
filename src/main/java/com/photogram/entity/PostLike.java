package com.photogram.entity;


import lombok.Data;

import java.sql.Timestamp;
@Data
public class PostLike {
    private Long id;
    private Long userId;
    private Long postId;
    private Timestamp likeTime;
}
