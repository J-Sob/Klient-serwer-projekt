package com.clientapplication;

import javax.swing.*;

public class Checkout {
    private JList<Product> productList;
    private JButton removeItemButton;
    private JTextField streetNameTextField;
    private JTextField houseNumberTextField;
    private JButton orderButton;
    private JLabel totalLabel;
    private JPanel MainPanel;
    private JFrame myFrame;

    Checkout(){
        myFrame = new JFrame("Checkout");
        myFrame.setContentPane(MainPanel);
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        productList.setModel(new DefaultListModel<Product>());
        productList.setListData(Login_Register.currentUser.getUsersProducts());



        myFrame.setSize(450,400);
        myFrame.setVisible(true);
    }
}
