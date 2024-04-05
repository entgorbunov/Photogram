package dao;


import entity.Post;
import entity.User;

import java.sql.Timestamp;

public class DaoRunner {
    public static void main(String[] args) {
        PostDao postDao = PostDao.getInstance();
        Post post = new Post(1L,
                new User(1L,
                        "Sasha",
                        "www",
                        "I'm human",
                        false,
                        "www"),
                "Was in Moscow",
                new Timestamp(System.currentTimeMillis()),
                "www"
        );

        postDao.save(post);
    }
}
