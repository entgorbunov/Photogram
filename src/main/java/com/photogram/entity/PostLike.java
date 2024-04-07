package com.photogram.entity;


import lombok.Data;

import java.sql.Timestamp;
@Data
public class PostLike {
    private int id;
    private int userId;
    private int postId;
    private Timestamp likeTime;
}
