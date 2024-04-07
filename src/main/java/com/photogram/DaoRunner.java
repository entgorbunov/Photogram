package com.photogram;


import com.photogram.dao.PostDao;
import com.photogram.dao.UserDao;
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




        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
