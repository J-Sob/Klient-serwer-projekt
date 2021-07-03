package com.clientapplication;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login_Register {
    private JButton logInButton;
    private JPanel MainPanel;
    private JTextField Login_TextField;
    private JPasswordField Password_TextField;
    private JTextField NewLogin_TextField;
    private JPasswordField NewPassword_TextField;
    private JButton RegisterButton;
    private JFrame myFrame;

    public static User currentUser;

    public Login_Register() {
        myFrame = new JFrame("Client Application");
        myFrame.setContentPane(MainPanel);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);

        logInButton.addActionListener(e -> {
            String login = Login_TextField.getText();
            String password = String.valueOf(Password_TextField.getPassword());
            try {
                LogInUser(login,password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
        RegisterButton.addActionListener(e -> {
            String login = NewLogin_TextField.getText();
            String password = String.valueOf(NewPassword_TextField.getPassword());
            try {
                RegisterUser(login,password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        myFrame.setSize(600,400);
        myFrame.setVisible(true);
    }
    private void LogInUser(String login, String password) throws SQLException {
        ServerConnection loginCheck = new ServerConnection("SELECT * FROM Users WHERE login='" + login +"'", ServerConnection.Action.retrieve);
        Thread loginCheckThread = new Thread(loginCheck);
        loginCheckThread.start();
        ResultSet resultSet = loginCheck.getResultSet();
        if(!resultSet.next()){
            JOptionPane.showMessageDialog(null,"No such user registered");
        }else{
            ServerConnection passwordVerification = new ServerConnection("SELECT * FROM Users WHERE password=sha1('" + password +"')", ServerConnection.Action.retrieve);
            Thread passwordVerificationThread = new Thread(passwordVerification);
            passwordVerificationThread.start();
            resultSet = passwordVerification.getResultSet();
            if(!resultSet.next()){
                JOptionPane.showMessageDialog(null,"Incorrect password");
            }else{
                int id = resultSet.getInt("id");
                currentUser = new User(id,login,password);
                HomeScreen homeScreen = new HomeScreen();
                myFrame.dispose();
            }
        }
    }

    private void RegisterUser(String login, String password) throws SQLException {
        ServerConnection loginCheck = new ServerConnection("SELECT * FROM Users WHERE login='" + login +"'", ServerConnection.Action.retrieve);
        Thread loginCheckThread = new Thread(loginCheck);
        loginCheckThread.start();
        ResultSet resultSet = loginCheck.getResultSet();
        if(!resultSet.next()){
            ServerConnection createUser = new ServerConnection("INSERT INTO `dbserver`.`Users`\n" +
                    "(`login`,\n" +
                    "`password`)\n" +
                    "VALUES\n" +
                    "('" + login + "',\n" +
                    "sha1('"+ password +"')\n" +
                    ");", ServerConnection.Action.update);
            Thread createUserThread = new Thread(createUser);
            createUserThread.start();
            int inserted = createUser.getStatementResult();

            JOptionPane.showMessageDialog(null,"Account created");
        } else {
            JOptionPane.showMessageDialog(null,"This login is already in use.");
        }

    }
}
