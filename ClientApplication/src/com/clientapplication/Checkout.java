package com.clientapplication;

import com.mysql.cj.log.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Checkout {
    private JList<Product> productList;
    private JButton removeItemButton;
    private JTextField streetNameTextField;
    private JTextField houseNumberTextField;
    private JButton orderButton;
    private JPanel MainPanel;
    private JLabel totalLabel;
    private JFrame myFrame;

    Checkout(){
        myFrame = new JFrame("Checkout");
        myFrame.setContentPane(MainPanel);
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        productList.setModel(new DefaultListModel<Product>());
        productList.setListData(Login_Register.currentUser.getUsersProducts());
        totalLabel.setText(String.valueOf(Login_Register.currentUser.getTotalPrice()) + " PLN");


        myFrame.setSize(450,400);
        myFrame.setVisible(true);

        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!productList.isSelectionEmpty()){
                    int index = productList.getSelectedIndex();
                    Login_Register.currentUser.removeProduct(index);
                    productList.setListData(Login_Register.currentUser.getUsersProducts());
                    totalLabel.setText(String.valueOf(Login_Register.currentUser.getTotalPrice()) + " PLN");
                }
            }
        });
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeOrder();
            }
        });
    }

    void makeOrder(){
        if(Login_Register.currentUser.getUsersProducts().isEmpty()){
            JOptionPane.showMessageDialog(null,"Your cart is empty. Add items first.");
        }
        else if(streetNameTextField.getText() == null || houseNumberTextField.getText() == null){
            JOptionPane.showMessageDialog(null,"Incorrect address");
        }
        else{
            Connection connection = ServerConnection.getConnection();
            Statement statement = null;
            try {
                statement = connection.createStatement();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            int userId = Login_Register.currentUser.getId();
            String orderContent = "";
            for(Product product : Login_Register.currentUser.getUsersProducts()){
                orderContent = orderContent + product.getProduct_name() + ", ";
            }
            double totalPrice = Login_Register.currentUser.getTotalPrice();
            String streetName = streetNameTextField.getText();
            int houseNumber = Integer.parseInt(houseNumberTextField.getText());
            try {
                statement.executeUpdate("INSERT INTO `dbserver`.`Orders`\n" +
                        "(`id_user`,\n" +
                        "`order_content`,\n" +
                        "`street_name`,\n" +
                        "`house_number`,\n" +
                        "`total_price`)\n" +
                        "VALUES\n" +
                        "(" + Login_Register.currentUser.getId() + ",\n" +
                        "'" + orderContent + "',\n" +
                        "'" + streetName + "',\n" +
                        "" + houseNumber + ",\n" +
                        "" + totalPrice + ");");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            Login_Register.currentUser.clearProducts();
            JOptionPane.showMessageDialog(null,"Order has been created. Hope you enjoy your meal!");
            myFrame.dispose();
        }
    }
}
