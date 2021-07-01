package com.clientapplication;

public class User {
    Integer ID;
    String login;
    String password;

    public User(Integer ID, String login, String password) {
        this.ID = ID;
        this.login = login;
        this.password = password;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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
}
