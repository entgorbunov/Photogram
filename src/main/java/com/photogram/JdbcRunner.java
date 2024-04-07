package com.photogram;

import com.photogram.daoException.DbConnectionException;
import com.photogram.util.ConnectionManager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;

public class JdbcRunner {
    public static void main(String[] args) {
        var driverClass = Driver.class;
        try (var connection = ConnectionManager.open()) {
            System.out.println(connection.getTransactionIsolation());
        } catch (SQLException e) {
            throw new DbConnectionException(e);
        }
    }
    
}
