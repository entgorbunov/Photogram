package com.photogram.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class CommentLike {
    private int id;
    private int userId;
    private int commentId;
    private Timestamp likeTime;
}
