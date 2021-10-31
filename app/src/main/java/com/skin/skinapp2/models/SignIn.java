package com.skin.skinapp2.models;

public class SignIn {
    String email;
    String password;

    public SignIn(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
