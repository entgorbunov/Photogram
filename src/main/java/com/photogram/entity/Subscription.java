package com.photogram.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class Subscription {
    private Long id;
    private Long followerId;
    private Long followingId;
    private Timestamp subscriptionTime;

}
