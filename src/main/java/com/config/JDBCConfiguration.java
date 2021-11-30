package com.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dao.DaoException;

@Configuration
public class JDBCConfiguration {

    @Value("${username}")
    private String username;

    private static String USERNAME;

    @Bean
    public static Connection getConnection() throws DaoException {
        String connectionURL = "jdbc:h2:tcp://localhost/~/test";
        String user = "sa";
        String password = "";

        System.out.println("Username is " + USERNAME);

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e2) {
            throw new DaoException("Impossible de se connecter à la base de données");
        }

        Connection connexion = null;
        try {
            connexion = DriverManager.getConnection(connectionURL, user, password);
            connexion.setAutoCommit(false);
        } catch (SQLException e1) {
            throw new DaoException("Impossible de se connecter à la base de données " + e1.getLocalizedMessage());
        }

        return connexion;
    }

    @Value("${username}")
    public void setUsername(String username) {
        JDBCConfiguration.USERNAME = username;
    }
}