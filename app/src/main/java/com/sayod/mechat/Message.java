package com.sayod.mechat;

public class Message {
    public String email;
    public String text;
    public String userid;

    // Empty constructor is needed for Firebase
    public Message() {}


    public Message(String text, String userid, String email) {
        this.text = text;
        this.userid = userid;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
