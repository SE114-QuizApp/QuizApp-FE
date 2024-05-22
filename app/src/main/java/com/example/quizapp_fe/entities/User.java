package com.example.quizapp_fe.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String _id;
    private String avatar;
    private String userType;
    private String firstName;
    private String lastName;
    private String userName;
    private String mail;
    private String isVerified;
    private int point;
    private ArrayList<String> friends;
    private ArrayList<String> follows;

    public User(String _id, String avatar, String userType, String firstName, String lastName, String userName, String mail, String isVerified, int point, ArrayList<String> friends, ArrayList<String> follows) {
        this._id = _id;
        this.avatar = avatar;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.mail = mail;
        this.isVerified = isVerified;
        this.point = point;
        this.friends = friends;
        this.follows = follows;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
    
}
