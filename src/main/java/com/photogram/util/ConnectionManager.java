package com.photogram.util;

import com.photogram.daoException.DbConnectionException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionManager {

    public static final String PASSWORD_KEY = "db.password";
    public static final String USERNAME_KEY = "db.username";
    public static final String  URL_KEY = "db.url";

    public static Connection open() {
        try {
            Properties properties = PropertiesUtil.loadProperties();
            String url = properties.getProperty(URL_KEY);
            String user = properties.getProperty(USERNAME_KEY);
            String password = properties.getProperty(PASSWORD_KEY);
            return DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            throw new DbConnectionException(e);
        }
    }



    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(Paths.get("src", "main", "resources", "application.properties").toString())) {
            properties.load(input);
        }
        return properties;
    }
}
