package com.clientapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerConnection {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/dbserver";
    private static final String DB_user_name = "dbuser";
    private static final String DB_password = "dbpassword";
    private static Connection connection;

    public static Connection getConnection(){
        try {
            connection = DriverManager.getConnection(DB_URL,DB_user_name,DB_password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
}
