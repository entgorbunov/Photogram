package com.photogram;


import com.photogram.dao.CommentDao;
import com.photogram.dao.ImageDao;
import com.photogram.dao.PostDao;
import com.photogram.dao.UserDao;
import com.photogram.entity.Image;
import com.photogram.entity.Post;
import com.photogram.entity.User;
import com.photogram.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public class DaoRunner {
    public static void main(String[] args) {


        try (Connection connection = ConnectionManager.open()) {
            UserDao userDao = UserDao.getInstance();
            PostDao postDao = PostDao.getInstance();
            CommentDao commentDao = CommentDao.getInstance();
            ImageDao imageDao = ImageDao.getInstance();
            imageDao.update(new Image(2L, "asdasdf",
                    new Post(1L), new User(1L),
                    new Timestamp(12)), connection);





        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
