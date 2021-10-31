package com.skin.skinapp2.models;

public class User {
    String first_name,last_name, email,password;

    public User(String firstName, String lastName, String email, String password) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
