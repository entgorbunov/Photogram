package com.photogram.dao;

import com.photogram.daoException.DaoException;
import com.photogram.entity.Post;
import com.photogram.util.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostDao implements Dao<Long, Post> {
    private static volatile PostDao instance;

    private final UserDao userDao = UserDao.getInstance();

    public static final String FIND_ALL_SQL = """
            SELECT posts.id,
                       posts.user_id,
                       posts.caption,
                       posts.post_time,
                       posts.image_url
                       from posts
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
                       where id = ?
            """;

    private static final String INSERT_NEW_POST = """
            INSERT INTO posts (id, user_id, caption, post_time, image_url) VALUES (?, ?, ?, ?, ?)
            """;

    public static final String UPDATE_POST = """
            UPDATE posts SET id = ?, user_id = ?, caption = ?, post_time = ?, image_url = ? WHERE id = ?
            """;

    public static final String DELETE_POST = """
            DELETE from posts where id = ?
            """;



    public static PostDao getInstance() {
        PostDao localInstance = instance;
        if (localInstance == null) {
            synchronized (PostDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PostDao();
                }
            }
        }
        return localInstance;
    }

    @Override
    public boolean delete(Long id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(DELETE_POST)) {
            preparedStatement.setLong(1, id);
            var changedData = preparedStatement.executeUpdate();
            if (changedData == 0) throw new DaoException("Post has not been deleted");
            return changedData > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void save(Post post, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_POST, Statement.RETURN_GENERATED_KEYS)) {
            var affectedRows = setInfoToPost(post, preparedStatement);
            if (affectedRows == 0) {
                throw new DaoException("Creating post failed");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getLong("id"));
                } else {
                    throw new DaoException("Creating post failed, no ID obtained");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Post post, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_POST)) {
            var affectedRows = setInfoToPost(post, preparedStatement);
            if (affectedRows == 0) {
                throw new DaoException("Updating post failed");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    private static int setInfoToPost(Post post, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, post.getId());
        preparedStatement.setLong(2, post.getUser().getId());
        preparedStatement.setString(3, post.getCaption());
        preparedStatement.setTimestamp(4, post.getPostTime());
        preparedStatement.setString(5, post.getImageUrl());
        preparedStatement.executeUpdate();
        return preparedStatement.executeUpdate();
    }


    @Override
    public Optional<Post> findById(Long id, Connection connection) {
        Post post = new Post();
        ResultSet resultSet;
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createPost(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return Optional.ofNullable(post);
    }


    @Override
    public List<Post> findAll(Connection connection) {
        List<Post> postList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL)) {
            while (resultSet.next()) {
                postList.add(createPost(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return postList;
    }


    private Post createPost(ResultSet resultSet) {
        try {
            return new Post(resultSet.getLong("id"),
                    userDao.findById(resultSet.getLong("user_id"), resultSet.getStatement().getConnection()).orElse(null),
                    resultSet.getString("caption"),
                    resultSet.getTimestamp("post_time"),
                    resultSet.getString("image_url"));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
