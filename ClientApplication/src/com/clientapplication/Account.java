package com.clientapplication;

import com.mysql.cj.log.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Account {
    private JLabel usersLoginLabel;
    private JList ordersList;
    private JPanel MainPanel;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JButton changePasswordButton;
    private JFrame myFrame;
    private Vector<String> orders;

    Account(){
        myFrame = new JFrame("Checkout");
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
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    changePassword();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    private void changePassword() throws SQLException {
        String currentPassword = String.valueOf(currentPasswordField.getPassword());
        String newPassword = String.valueOf(newPasswordField.getPassword());
        Connection connection = ServerConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Users WHERE id = "+ Login_Register.currentUser.getId() +" " +
                "                                       and password = sha1('"+ currentPassword + "')");
        if(rs.next()){
            statement.executeUpdate("UPDATE `dbserver`.`Users`\n" +
                    "SET\n" +
                    "`password` = sha1('"+ newPassword +"')\n" +
                    "WHERE `id` = " + Login_Register.currentUser.getId() +"");
            JOptionPane.showMessageDialog(null,"Password changed.");
        }
    }

    private Vector<String> retrieveData() throws SQLException {
        Vector<String> orders = new Vector<>();
        Connection connection = ServerConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Orders WHERE id_user =" + Login_Register.currentUser.getId());
        while(rs.next()){
            String order = rs.getString("order_content") + "  -  " + rs.getString("total_price") + " PLN";
            orders.add(order);
        }
        return orders;
    }
}
