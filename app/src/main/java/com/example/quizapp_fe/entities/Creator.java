package com.example.quizapp_fe.entities;

public class Creator {
    private String _id;
    private String avatar;
    private String userType;
    private String firstName;
    private String lastName;
    private String userName;

    public String getFullName() {
        return lastName + " " + firstName;
    }
}
