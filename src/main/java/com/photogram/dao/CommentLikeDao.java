package com.photogram.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentLikeDao {
    private static volatile CommentLikeDao instance;

    public static CommentLikeDao getInstance() {
        if (instance == null) {
            synchronized (CommentLikeDao.class) {
                if (instance == null) {
                    instance = new CommentLikeDao();
                }
            }
        }
        return instance;
    }
    // DAO methods
}