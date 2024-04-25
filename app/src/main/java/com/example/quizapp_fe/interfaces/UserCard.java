package com.example.quizapp_fe.interfaces;

public class UserCard {
    private int userCardImage;
    private String userCardName;
    private String userCardEmail;

    public UserCard(int userCardImage, String userCardName, String userCardEmail) {
        this.userCardImage = userCardImage;
        this.userCardName = userCardName;
        this.userCardEmail = userCardEmail;
    }

    public int getUserCardImage() {
        return userCardImage;
    }

    public String getUserCardName() {
        return userCardName;
    }

    public String getUserCardEmail() {
        return userCardEmail;
    }
}
