package com.example.capstone_0443.Model;

public class User {

    public String password, email, username;

    public User(){

    }

    public User(String email, String username,String password) {

        this.email = email;
        this.username = username;
        this.password = password;
    }
}
