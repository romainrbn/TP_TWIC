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

    @Value("${database.user}")
    private static String USERNAME;
    @Value("${database.password}")
    private static String PASSWORD;

    @Bean
    public static Connection getConnection() throws DaoException {
        String connectionURL = "jdbc:h2:tcp://localhost/~/test";
        try (Connection connexion = DriverManager.getConnection(connectionURL,USERNAME,PASSWORD)){
            connexion.setAutoCommit(false);
            return connexion;
        } catch (SQLException e1) {
            throw new DaoException("Impossible de se connecter à la base de données " + e1.getLocalizedMessage());
        }

    }

    @Value("${database.user}")
    public synchronized void setUserP(String user){
        JDBCConfiguration.USERNAME = user;
    }
}