package com.photogram.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class Subscription {
    private int id;
    private int followerId;
    private int followingId;
    private Timestamp subscriptionTime;

}
