package com.photogram.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {

    boolean delete(K id, Connection connection);

    void save(E entity, Connection connection);

    void update(E entity, Connection connection);

    Optional<E> findById(K id, Connection connection);

    List<E> findAll(Connection connection);
}
