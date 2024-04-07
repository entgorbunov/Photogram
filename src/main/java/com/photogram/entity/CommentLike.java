package com.photogram.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class CommentLike {
    private Long id;
    private Long userId;
    private Long commentId;
    private Timestamp likeTime;
}
