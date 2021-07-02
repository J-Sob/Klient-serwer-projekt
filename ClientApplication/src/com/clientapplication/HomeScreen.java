package com.clientapplication;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class HomeScreen {
    private enum productTypes{
        burger,
        pancake,
        fries,
        all,
    }

    private JScrollPane scrollPane;
    private JPanel MainPanel;
    private JButton allProductsButton;
    private JButton burgersButton;
    private JButton pancakesButton;
    private JButton friesButton;
    private JList<Product> productList;
    private JButton checkoutButton;
    private JButton addToCartButton;
    private JButton accountButton;
    private JFrame myFrame;
    private Vector<Product> products;

    HomeScreen(){
        myFrame = new JFrame("Client Application");
        myFrame.setContentPane(MainPanel);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        productList.setModel(new DefaultListModel<Product>());

        try {
            products = retrieveProducts(productTypes.all);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        productList.setListData(products);

        myFrame.setSize(650,450);
        myFrame.setVisible(true);
        allProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    products.clear();
                    products = retrieveProducts(productTypes.all);
                    productList.setListData(products);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        burgersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    products.clear();
                    products = retrieveProducts(productTypes.burger);
                    productList.setListData(products);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        pancakesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    products.clear();
                    products = retrieveProducts(productTypes.pancake);
                    productList.setListData(products);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        friesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    products.clear();
                    products = retrieveProducts(productTypes.fries);
                    productList.setListData(products);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!productList.isSelectionEmpty()){
                    Product product = productList.getSelectedValue();
                    Login_Register.currentUser.addProduct(product);
                }
            }
        });
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Checkout checkout = new Checkout();
            }
        });
    }

    private Vector<Product> retrieveProducts(productTypes type) throws SQLException {
        Vector<Product> products = new Vector<>();
        Connection connection = ServerConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs;
        switch (type){
            case fries ->  rs = statement.executeQuery("SELECT * FROM Products WHERE product_type = 'fries'");
            case burger -> rs = statement.executeQuery("SELECT * FROM Products WHERE product_type = 'burger'");
            case pancake -> rs = statement.executeQuery("SELECT * FROM Products WHERE product_type = 'pancakes'");
            default -> rs = statement.executeQuery("SELECT * FROM Products");
        }

        while(rs.next()){
            Product product = new Product(rs.getString("product_name"),rs.getString("product_type"),rs.getDouble("price"));
            products.add(product);
        }
        return products;
    }
}
