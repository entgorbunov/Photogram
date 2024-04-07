package com.photogram.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubscriptionDao {
    private static volatile SubscriptionDao instance;

    public static SubscriptionDao getInstance() {
        if (instance == null) {
            synchronized (SubscriptionDao.class) {
                if (instance == null) {
                    instance = new SubscriptionDao();
                }
            }
        }
        return instance;
    }
    // DAO methods
}
