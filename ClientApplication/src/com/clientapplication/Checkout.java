package com.clientapplication;


import javax.swing.*;

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
        productList.setModel(new DefaultListModel<>());
        productList.setListData(Login_Register.currentUser.getUsersProducts());
        totalLabel.setText(Login_Register.currentUser.getTotalPrice() + " PLN");


        myFrame.setSize(450,400);
        myFrame.setVisible(true);

        removeItemButton.addActionListener(e -> {
            if(!productList.isSelectionEmpty()){
                int index = productList.getSelectedIndex();
                Login_Register.currentUser.removeProduct(index);
                productList.setListData(Login_Register.currentUser.getUsersProducts());
                totalLabel.setText(Login_Register.currentUser.getTotalPrice() + " PLN");
            }
        });
        orderButton.addActionListener(e -> makeOrder());
    }

    void makeOrder(){
        if(streetNameTextField.getText() == null || houseNumberTextField.getText() == null){
            JOptionPane.showMessageDialog(null,"Incorrect address");
        } else {
            int userId = Login_Register.currentUser.getId();
            String orderContent = "";
            for(Product product : Login_Register.currentUser.getUsersProducts()){
                orderContent = orderContent + product.getProduct_name() + ", ";
            }
            double totalPrice = Login_Register.currentUser.getTotalPrice();
            String streetName = streetNameTextField.getText();
            int houseNumber = Integer.parseInt(houseNumberTextField.getText());
            ServerConnection createOrder = new ServerConnection("INSERT INTO `dbserver`.`Orders`\n" +
                    "(`id_user`,\n" +
                    "`order_content`,\n" +
                    "`street_name`,\n" +
                    "`house_number`,\n" +
                    "`total_price`)\n" +
                    "VALUES\n" +
                    "(" + userId + ",\n" +
                    "'" + orderContent + "',\n" +
                    "'" + streetName + "',\n" +
                    "" + houseNumber + ",\n" +
                    "" + totalPrice + ");", ServerConnection.Action.update);
            Thread createOrderThread = new Thread(createOrder);
            createOrderThread.start();
            Login_Register.currentUser.clearProducts();
            JOptionPane.showMessageDialog(null,"Order has been created. Hope you enjoy your meal!");
            myFrame.dispose();
        }
    }
}
