package com.clientapplication;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private JButton logOutButton;
    private JFrame myFrame;
    private Vector<Product> products;

    HomeScreen(){
        myFrame = new JFrame("Client Application");
        myFrame.setContentPane(MainPanel);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        productList.setModel(new DefaultListModel<>());

        try {
            products = retrieveProducts(productTypes.all);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        productList.setListData(products);

        myFrame.setSize(650,450);
        myFrame.setVisible(true);
        allProductsButton.addActionListener(e -> {
            try {
                products.clear();
                products = retrieveProducts(productTypes.all);
                productList.setListData(products);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        burgersButton.addActionListener(e -> {
            try {
                products.clear();
                products = retrieveProducts(productTypes.burger);
                productList.setListData(products);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        pancakesButton.addActionListener(e -> {
            try {
                products.clear();
                products = retrieveProducts(productTypes.pancake);
                productList.setListData(products);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        friesButton.addActionListener(e -> {
            try {
                products.clear();
                products = retrieveProducts(productTypes.fries);
                productList.setListData(products);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        addToCartButton.addActionListener(e -> {
            if(!productList.isSelectionEmpty()){
                Product product = productList.getSelectedValue();
                Login_Register.currentUser.addProduct(product);
            }
        });
        checkoutButton.addActionListener(e -> {
            if(Login_Register.currentUser.getUsersProducts().isEmpty()){
                JOptionPane.showMessageDialog(null,"Your cart is empty. Add items first.");
            }else {
                Checkout checkout = new Checkout();
            }
        });
        logOutButton.addActionListener(e -> {
            Login_Register loginRegister = new Login_Register();
            myFrame.dispose();
        });
        accountButton.addActionListener(e -> {
            Account account = new Account();
        });
    }

    private Vector<Product> retrieveProducts(productTypes type) throws SQLException {
        Vector<Product> products = new Vector<>();
        String sql;
        switch (type){
            case fries ->  sql = "SELECT * FROM Products WHERE product_type = 'fries'";
            case burger -> sql = "SELECT * FROM Products WHERE product_type = 'burger'";
            case pancake -> sql = "SELECT * FROM Products WHERE product_type = 'pancakes'";
            default -> sql = "SELECT * FROM Products";
        }
        ServerConnection retrieveProducts = new ServerConnection(sql, ServerConnection.Action.retrieve);
        Thread retrieveProductsThread = new Thread(retrieveProducts);
        retrieveProductsThread.start();
        try {
            retrieveProductsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ResultSet rs = retrieveProducts.getResultSet();
        while(rs.next()){
            Product product = new Product(rs.getString("product_name"),rs.getString("product_type"),rs.getDouble("price"));
            products.add(product);
        }
        return products;
    }
}
