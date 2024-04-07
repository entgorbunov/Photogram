package com.photogram.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostLikeDao {
    private static volatile PostLikeDao instance;

    public static PostLikeDao getInstance() {
        if (instance == null) {
            synchronized (PostLikeDao.class) {
                if (instance == null) {
                    instance = new PostLikeDao();
                }
            }
        }
        return instance;
    }
    // DAO methods
}
