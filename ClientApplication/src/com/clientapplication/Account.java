package com.clientapplication;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Account {
    private JLabel usersLoginLabel;
    private JList ordersList;
    private JPanel MainPanel;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JButton changePasswordButton;
    private JFrame myFrame;

    Account(){
        myFrame = new JFrame("Account");
        myFrame.setContentPane(MainPanel);
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setSize(300,500);
        myFrame.setVisible(true);
        usersLoginLabel.setText(Login_Register.currentUser.getLogin());

        ordersList.setModel(new DefaultListModel());
        try {
            ordersList.setListData(retrieveData());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        changePasswordButton.addActionListener(e -> {
            try {
                changePassword();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void changePassword() throws SQLException {
        String currentPassword = String.valueOf(currentPasswordField.getPassword());
        String newPassword = String.valueOf(newPasswordField.getPassword());
        ServerConnection passwordVerification = new ServerConnection("SELECT * FROM Users WHERE id = "+ Login_Register.currentUser.getId() +" " +
                "                                       and password = sha1('"+ currentPassword + "')", ServerConnection.Action.retrieve);
        Thread passwordVerificationThread = new Thread(passwordVerification);
        passwordVerificationThread.start();
        ResultSet rs = passwordVerification.getResultSet();
        if(rs.next()){
            ServerConnection passwordUpdate = new ServerConnection("UPDATE `dbserver`.`Users`\n" +
                    "SET\n" +
                    "`password` = sha1('"+ newPassword +"')\n" +
                    "WHERE `id` = " + Login_Register.currentUser.getId() +"", ServerConnection.Action.update);
            Thread passwordUpdateThread = new Thread(passwordUpdate);
            passwordUpdateThread.start();
            JOptionPane.showMessageDialog(null,"Password changed.");
        }else{
            JOptionPane.showMessageDialog(null,"Incorrect password.");
        }
    }

    private Vector<String> retrieveData() throws SQLException {
        Vector<String> orders = new Vector<>();
        ServerConnection retrieveOrders = new ServerConnection("SELECT * FROM Orders WHERE id_user =" + Login_Register.currentUser.getId(), ServerConnection.Action.retrieve);
        Thread retrieveOrdersThread = new Thread(retrieveOrders);
        retrieveOrdersThread.start();
        ResultSet rs = retrieveOrders.getResultSet();
        while(rs.next()){
            String order = rs.getString("order_content") + "  -  " + rs.getString("total_price") + " PLN";
            orders.add(order);
        }
        return orders;
    }
}
