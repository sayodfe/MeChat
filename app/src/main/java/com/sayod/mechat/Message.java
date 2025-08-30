package com.sayod.mechat;

public class Message {
    public String username;
    public String text;

    // Empty constructor is needed for Firebase
    public Message() {}

    public Message(String username, String text) {
        this.username = username;
        this.text = text;
    }
}
