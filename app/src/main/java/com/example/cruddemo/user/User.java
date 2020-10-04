package com.example.cruddemo.user;

public class User {
    private String userName;
    private String phone;
    private String email;

    public User(){}

    public String getUserName() {
        return userName;
    }


    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
