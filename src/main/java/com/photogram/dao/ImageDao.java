package com.photogram.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageDao {
    private static volatile ImageDao instance;

    public static ImageDao getInstance() {
        if (instance == null) {
            synchronized (ImageDao.class) {
                if (instance == null) {
                    instance = new ImageDao();
                }
            }
        }
        return instance;
    }
    // DAO methods
}
