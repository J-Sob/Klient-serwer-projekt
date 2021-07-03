package com.clientapplication;

import java.sql.*;

public class ServerConnection implements Runnable {
    private final String DB_URL = "jdbc:mysql://127.0.0.1:3306/dbserver";
    private final String DB_user_name = "dbuser";
    private final String DB_password = "dbpassword";
    private Connection connection;
    private Statement statement;
    private int statementResult;
    private ResultSet resultSet;
    private String SQL;
    private Action action;

    public enum Action{
        retrieve,
        update
    }

    public ServerConnection(String SQL, Action action){
        this.SQL = SQL;
        this.action = action;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public int getStatementResult() {
        return statementResult;
    }

    @Override
    public void run() {
        try {
            connection = DriverManager.getConnection(DB_URL,DB_user_name,DB_password);
            statement = connection.createStatement();
            if(this.action == Action.retrieve){
                resultSet = statement.executeQuery(this.SQL);
            }
            if(this.action == Action.update){
                statementResult = statement.executeUpdate(this.SQL);
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
