package com.photogram;


import com.photogram.dao.PostDao;
import com.photogram.dao.UserDao;
import com.photogram.entity.Post;
import com.photogram.entity.User;
import com.photogram.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class DaoRunner {
    public static void main(String[] args) {


        User[] users = {
                new User(0, "Алексей Небесный", "/Users/entgorbunov/Desktop/Images/Алексей Небесный - Астроном-любитель, мечтающий о путешествии на Марс..png", "Астроном-любитель, мечтающий о путешествии на Марс.", false, null),

                new User(0, "Ирина Зеленая", "/Users/entgorbunov/Desktop/Images/Ирина Зеленая - Садовник с зелёными пальцами, влюблённый в редкие орхидеи..png", "Садовник с зелёными пальцами, влюблённый в редкие орхидеи.", false, null),

                new User(0, "Максим Кодиров", "/Users/entgorbunov/Desktop/Images/Максим Кодиров - Разработчик ПО, ночью пишущий код..png", "Разработчик ПО, ночью пишущий код, днём спасающий мир.", false, null),

                new User(0, "Екатерина Защитникова", "/Users/entgorbunov/Desktop/Images/Екатерина Защитникова - Экоактивист, борющийся за сохранение природы для будущих поколений..png", "Экоактивист, борющийся за сохранение природы для будущих поколений.", false, null),

                new User(0, "Андрей Историков", "/Users/entgorbunov/Desktop/Images/Андрей Историков - Историк, увлекающийся реконструкцией событий Средневековья..png", "Историк, увлекающийся реконструкцией событий Средневековья.", false, null),

                new User(0, "Виктор Вкусов", "/Users/entgorbunov/Desktop/Images/Виктор Вкусов - Гурман, путешествующий за уникальными рецептами..png", "Гурман, путешествующий за уникальными рецептами.", false, null),

                new User(0, "Дмитрий странник", "/Users/entgorbunov/Desktop/Images/Дмитрий Странник - Цифровой кочевник, работающий из разных уголков мира..png", "Цифровой кочевник, работающий из разных уголков мира.", false, null),

                new User(0, "Елена Квантова", "/Users/entgorbunov/Desktop/Images/Елена Квантова - Физик-теоретик, исследующий тайны квантового мира..png", "Физик-теоретик, исследующий тайны квантового мира.", false, null),

                new User(0, "Николай Звездочетов", "/Users/entgorbunov/Desktop/Images/Николай Звездочётов - Фотограф ночного неба, вечно в поисках совершенного снимка Млечного Пути.png", "Фотограф ночного неба, вечно в поисках совершенного снимка Млечного Пути", false, null),

                new User(0, "Светлана Расследова", "/Users/entgorbunov/Desktop/Images/Светлана Расследова - Писатель детективов, чьи сюжеты захватывают с первой страницы..png", "Писатель детективов, чьи сюжеты захватывают с первой страницы.", false, null)


        };

        try (Connection connection = ConnectionManager.open()) {
            UserDao userDao = UserDao.getInstance();

            for (User user : users) {
                userDao.save(user, connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
