package com.clientapplication;

public class Product {
    String product_name;
    String product_type;
    Double price;

    public Product(String product_name, String product_type, Double price) {
        this.product_name = product_name;
        this.product_type = product_type;
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return getProduct_name() + "  -  " + Double.toString(getPrice()) + " PLN";
    }
}
