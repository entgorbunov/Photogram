package dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentDao {
    private static volatile CommentDao instance;

    public static CommentDao getInstance() {
        if (instance == null) {
            synchronized (CommentDao.class) {
                if (instance == null) {
                    instance = new CommentDao();
                }
            }
        }
        return instance;
    }
    // DAO methods
}

