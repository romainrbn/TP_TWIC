package com.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dao.DaoException;

@Configuration
public class JDBCConfiguration {

    @Bean
    public static Connection getConnection() throws DaoException {
        String connectionURL = "jdbc:mysql://localhost:3306/ProjetTWIC?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
        String user = "root";
        String pwd = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e2) {
            throw new DaoException("Impossible de se connecter à la base de données");
        }

        Connection connexion = null;
        try {
            connexion = DriverManager.getConnection(connectionURL,user,pwd);
            connexion.setAutoCommit(false);
        } catch (SQLException e1) {
            throw new DaoException("Impossible de se connecter à la base de données " + e1.getLocalizedMessage());
        }

        return connexion;
    }
}