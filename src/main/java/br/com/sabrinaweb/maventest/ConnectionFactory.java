package br.com.sabrinaweb.maventest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/loja";
        String username = "root";
        String password = "root";
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
        }
        return null;
    }
}