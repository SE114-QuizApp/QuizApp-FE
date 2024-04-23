package com.example.quizapp_fe.entities;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private String _id;
    private String userName;
    private String mail;
    private String firstName;
    private String lastName;
    private String avatar;
    private String userType;
    private Integer point;


public UserProfile(String _id, String userName, String mail, String firstName, String lastName, String avatar, String userType, Integer point) {
        this._id = _id;
        this.userName = userName;
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.userType = userType;
        this.point = point;
    }

    public String getId() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }



}
