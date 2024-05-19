package com.example.quizapp_fe.entities;

import java.io.Serializable;

public class DiscoverCreator implements Serializable {
    private String userName;
    private String firstName;
    private String lastName;
    private String avatar;
    private String userType;

    public DiscoverCreator(String userName, String firstName, String lastName, String avatar, String userType) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
