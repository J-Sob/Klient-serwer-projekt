package com.clientapplication;

import java.util.Vector;

public class User {

    int id;
    String login;
    String password;
    Vector<Product> usersProducts;
    double totalPrice;

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.totalPrice = 0;
        this.usersProducts = new Vector<>();
    }

    public void addProduct(Product product){
        this.usersProducts.add(product);
        totalPrice += product.getPrice();
    }

    public void removeProduct(int index){
        totalPrice -= usersProducts.get(index).getPrice();
        this.usersProducts.remove(index);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vector<Product> getUsersProducts() {
        return usersProducts;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
