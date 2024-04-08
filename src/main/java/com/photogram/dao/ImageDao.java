package com.photogram.dao;

import com.photogram.daoException.DaoException;
import com.photogram.entity.Image;
import com.photogram.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageDao implements Dao<Long, Image> {
    private static volatile ImageDao instance;
    private final PostDao postDao = PostDao.getInstance();
    private final UserDao userDao = UserDao.getInstance();

    private static final String FIND_ALL_SQL = """
            SELECT id, path, post_id, user_id, uploaded_time
            FROM photogram.public.image
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String INSERT_NEW_IMAGE = """
            INSERT INTO photogram.public.image (id, path, post_id, user_id, uploaded_time) VALUES (?, ?, ?, ?, ?)
            """;

    private static final String UPDATE_IMAGE = """
            UPDATE photogram.public.image SET post_id = ?, user_id = ?, path = ?, uploaded_time = ? WHERE id = ?
            """;

    private static final String DELETE_IMAGE = """
            DELETE from photogram.public.image where id = ?
            """;

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

    @Override
    public boolean delete(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_IMAGE)) {
            preparedStatement.setLong(1, id);
            int changedData = preparedStatement.executeUpdate();
            return changedData > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void save(Image image, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_IMAGE, Statement.RETURN_GENERATED_KEYS)) {
            var affectedRows = setInfoToImage(image, preparedStatement);
            if (affectedRows == 0) {
                throw new DaoException("Creating comment failed");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    image.setId(generatedKeys.getLong(1));
                } else {
                    throw new DaoException("Creating image failed, no ID obtained");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static int setInfoToImage(Image image, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, image.getId());
        preparedStatement.setString(2, image.getPath());
        preparedStatement.setLong(3, image.getPostId().getId());
        preparedStatement.setLong(4, image.getUserId().getId());
        preparedStatement.setTimestamp(5, image.getUploadedTime());
        return preparedStatement.executeUpdate();
    }



    @Override
    public void update(Image image, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_IMAGE)) {
            var affectedRows = setInfoToImage(image, preparedStatement);
            if (affectedRows == 0) {
                throw new DaoException("Updating post failed");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Image> findById(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(createImage(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Image> findAll(Connection connection) {
        List<Image> images = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL)) {
            while (resultSet.next()) {
                images.add(createImage(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return images;
    }

    private Image createImage(ResultSet resultSet) {
        try {
            return new Image(resultSet.getLong("id"),
                    resultSet.getString("path"),
                    postDao.findById(resultSet.getLong("post_id"), resultSet.getStatement().getConnection()).orElseThrow(),
                    userDao.findById(resultSet.getLong("user_id"), resultSet.getStatement().getConnection()).orElseThrow(),
                    resultSet.getTimestamp("uploaded_time"));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
