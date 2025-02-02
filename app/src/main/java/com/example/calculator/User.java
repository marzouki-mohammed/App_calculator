package com.example.calculator;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String password;

    // Default constructor (required for Firebase or deserialization)
    public User() {
    }

    // Constructor with parameters
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}