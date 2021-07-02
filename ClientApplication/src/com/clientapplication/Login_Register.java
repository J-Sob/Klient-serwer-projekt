package com.clientapplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login_Register {
    private JButton logInButton;
    private JPanel MainPanel;
    private JTextField Login_TextField;
    private JPasswordField Password_TextField;
    private JTextField NewLogin_TextField;
    private JPasswordField NewPassword_TextField;
    private JButton RegisterButton;

    public Login_Register() throws SQLException {
        JFrame jFrame = new JFrame("Client Application");
        jFrame.setContentPane(MainPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);

        ResultSet resultSet;
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = Login_TextField.getText();
                String password = String.valueOf(Password_TextField.getPassword());
                try {
                    LogInUser(login,password);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        RegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = NewLogin_TextField.getText();
                String password = String.valueOf(NewPassword_TextField.getPassword());
                try {
                    RegisterUser(login,password);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        jFrame.setSize(600,400);
        jFrame.setVisible(true);
    }
    private void LogInUser(String login, String password) throws SQLException {
        Connection connection = ServerConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet;
        resultSet = statement.executeQuery("SELECT * FROM Users WHERE login='" + login +"'");
        if(!resultSet.next()){
            JOptionPane.showMessageDialog(null,"No such user registered");
        }else{
            resultSet = statement.executeQuery("SELECT * FROM Users WHERE password=sha1('" + password +"')");
            if(!resultSet.next()){
                JOptionPane.showMessageDialog(null,"Incorrect password");
            }else{
                JOptionPane.showMessageDialog(null,"Log in successful!");
            }
        }
    }

    private void RegisterUser(String login, String password) throws SQLException {
        Connection connection = ServerConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet;
        resultSet = statement.executeQuery("SELECT * FROM Users WHERE login='" + login +"'");
        if(!resultSet.next()){
            try {
                int inserted = statement.executeUpdate("INSERT INTO `dbserver`.`Users`\n" +
                        "(`login`,\n" +
                        "`password`)\n" +
                        "VALUES\n" +
                        "('" + login + "',\n" +
                        "sha1('"+ password +"')\n" +
                        ");\n");
                if(inserted == 1){
                    JOptionPane.showMessageDialog(null,"Account created");
                }else{
                    JOptionPane.showMessageDialog(null,"Something went wrong. Try again.");
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null,"This login is already in use.");
        }

    }

    public static void main(String[] args) throws SQLException {
        Login_Register login_register = new Login_Register();
    }
}
