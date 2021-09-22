package dev.mckinney.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {

    public Connection establishConnection() throws SQLException {
        try {
            // registering our JDBC driver in the classpath
            Class.forName("org.postgresql.Driver");


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "Pass?the?potatoes";
        return DriverManager.getConnection(url, username, password);
    }
}
