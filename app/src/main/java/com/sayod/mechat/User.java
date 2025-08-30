package com.sayod.mechat;

public class User {
    public String name;
    public String email;

    // Empty constructor (required for Firebase)
    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
