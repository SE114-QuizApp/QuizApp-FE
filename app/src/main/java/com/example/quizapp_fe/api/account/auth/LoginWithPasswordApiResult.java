package com.example.quizapp_fe.api.account.auth;

import androidx.annotation.NonNull;

import com.example.quizapp_fe.entities.UserProfile;

import java.io.Serializable;

public class LoginWithPasswordApiResult implements Serializable {
    public String accessToken;
    public String refreshToken;
    public UserProfile user;


    public String getAccessToken() {
        return accessToken;
    }


    public String getRefreshToken() {
        return refreshToken;
    }


    public UserProfile getUser() {
        return user;
    }

}
