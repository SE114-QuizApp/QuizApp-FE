package com.example.quizapp_fe.interfaces;

public class UserCard {
    private String userCardImage;
    private String userCardName;
    private String userCardEmail;
    private String userCardId;

    public UserCard(String userCardImage, String userCardName, String userCardEmail, String userCardId) {
        this.userCardImage = userCardImage;
        this.userCardName = userCardName;
        this.userCardEmail = userCardEmail;
        this.userCardId = userCardId;
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

    public String getUserCardId() {
        return userCardId;
    }
}
