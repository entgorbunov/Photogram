package dao;

import daoException.DaoException;
import entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao implements Dao<Long, User> {
    private static volatile UserDao instance = new UserDao();
    private Connection connection;


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
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_USERS)) {
            while (rs.next()) {
                users.add(createUser(rs));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return users;
    }

    private User createUser(ResultSet rs) throws SQLException {
        return new User(rs.getLong("id"),
                rs.getString("username"),
                rs.getString("profile_picture"),
                rs.getString("bio"),
                rs.getBoolean("is_private"),
                rs.getString("image_url"));
    }


    @Override
    public Optional<User> findById(Long id) {
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
    public User save(User user) {
        try (PreparedStatement pstmt = connection.prepareStatement(INSERT_NEW_USER, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getProfilePicture());
            pstmt.setString(3, user.getBio());
            pstmt.setBoolean(4, user.isPrivate());
            pstmt.setString(5, user.getImageUrl());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating user failed.");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new DaoException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }

    @Override
    public void update(User user) {
        try (PreparedStatement pstmt = connection.prepareStatement(UPDATE_USER)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getProfilePicture());
            pstmt.setString(3, user.getBio());
            pstmt.setBoolean(4, user.isPrivate());
            pstmt.setString(5, user.getImageUrl());
            pstmt.setLong(6, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
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
