package com.photogram.dao;

import com.photogram.daoException.DaoException;
import com.photogram.entity.User;
import com.photogram.util.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao implements Dao<Long, User> {
    private static volatile UserDao instance = new UserDao();

    private static final String SELECT_ALL_USERS = "SELECT id, username, profile_picture, bio, is_private, image_url FROM " +
                                                   "Users";
    private static final String SELECT_USER_BY_ID = SELECT_ALL_USERS + " WHERE id = ? ";
    private static final String INSERT_NEW_USER = "INSERT INTO Users (username, profile_picture, bio, is_private, image_url) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE Users SET username = ?, profile_picture = ?, bio = ?, is_private = ?, image_url = ? WHERE id = ?";
        private static final String DELETE_USER = "DELETE FROM Users WHERE id = ?";


    public static UserDao getInstance() {
        if (instance == null) {
            synchronized (UserDao.class) {
                if (instance == null) {
                    instance = new UserDao();
                }
            }
        }
        return instance;
    }

    @Override
    public List<User> findAll(Connection connection) {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS)) {
            while (resultSet.next()) {
                users.add(createUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return users;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getLong("id"),
                resultSet.getString("username"),
                resultSet.getString("profile_picture"),
                resultSet.getString("bio"),
                resultSet.getBoolean("is_private"),
                resultSet.getString("image_url"));
    }


    @Override
    public Optional<User> findById(Long id, Connection connection) {
        User user = null;
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public void save(User user, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getProfilePicture());
            preparedStatement.setString(3, user.getBio());
            preparedStatement.setBoolean(4, user.isPrivate());
            preparedStatement.setString(5, user.getImageUrl());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating user failed.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new DaoException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(User user, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getProfilePicture());
            preparedStatement.setString(3, user.getBio());
            preparedStatement.setBoolean(4, user.isPrivate());
            preparedStatement.setString(5, user.getImageUrl());
            preparedStatement.setLong(6, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, id);
            int changedData = preparedStatement.executeUpdate();
            if (changedData == 0) throw new DaoException("User has not been deleted");
            return changedData > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
