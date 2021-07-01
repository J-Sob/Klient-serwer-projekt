package com.clientapplication;

import java.sql.*;

public class Main {
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/dbserver";
    static final String DB_user_name = "dbuser";
    static final String DB_password = "dbpassword";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL,DB_user_name,DB_password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Users");
        if(!resultSet.next()){
            System.out.println("Table empty");
        }else{
            do{
                System.out.println(resultSet.getString("id") + " " + resultSet.getString("login"));
            }while(resultSet.next());
        }
        connection.close();
    }

}
