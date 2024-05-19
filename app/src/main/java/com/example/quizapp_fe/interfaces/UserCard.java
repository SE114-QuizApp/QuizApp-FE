package com.example.quizapp_fe.interfaces;

public class UserCard {
    private String userCardImage;
    private String userCardName;
    private String userCardEmail;

    public UserCard(String userCardImage, String userCardName, String userCardEmail) {
        this.userCardImage = userCardImage;
        this.userCardName = userCardName;
        this.userCardEmail = userCardEmail;
    }

    public String getUserCardImage() {
        return userCardImage;
    }

    public String getUserCardName() {
        return userCardName;
    }

    public String getUserCardEmail() {
        return userCardEmail;
    }
}
