package com.example.quizapp_fe.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.quizapp_fe.entities.UserProfile;
import com.example.quizapp_fe.utils.MySharedPreferences;

public class CredentialToken {
    private Context context;
    private static CredentialToken instance;

    private String user_id;
    private String accessToken;
    private String refreshToken;
    private UserProfile userProfile;

    public CredentialToken() {
    }

    public static CredentialToken getInstance(Context context) {
        if (instance == null) {
            instance = new CredentialToken(context);
        }
        return instance;
    }

    public CredentialToken(Context context) {
        this.context = context;
        MySharedPreferences mySharedPreferences = new MySharedPreferences(context);
        this.user_id = mySharedPreferences.getSharedPreferences().getString("user_id", "");
        this.accessToken = mySharedPreferences.getSharedPreferences().getString("accessToken", "");
        this.refreshToken = mySharedPreferences.getSharedPreferences().getString("refreshToken", "");
        updateUserProfile();
    }

    private void updateUserProfile() {
        MySharedPreferences mySharedPreferences = new MySharedPreferences(context);
        this.userProfile = new UserProfile(
                mySharedPreferences.getSharedPreferences().getString("user_id", ""),
                mySharedPreferences.getSharedPreferences().getString("username", ""),
                mySharedPreferences.getSharedPreferences().getString("mail", ""),
                mySharedPreferences.getSharedPreferences().getString("fistName", ""),
                mySharedPreferences.getSharedPreferences().getString("lastName", ""),
                mySharedPreferences.getSharedPreferences().getString("avatar", ""),
                mySharedPreferences.getSharedPreferences().getString("userType", ""),
                0
        );
    }

    public void setCredential(String user_id, String accessToken, String refreshToken, UserProfile userProfile) {
        this.user_id = user_id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userProfile = userProfile;
        MySharedPreferences mySharedPreferences = new MySharedPreferences(context);
        System.out.println(mySharedPreferences.toString());
        SharedPreferences.Editor editor = mySharedPreferences.getEditor();
        editor.putString("user_id", user_id);
        editor.putString("accessToken", accessToken);
        editor.putString("refreshToken", refreshToken);
        editor.putString("username", userProfile.getUsername());
        editor.putString("mail", userProfile.getMail());
        editor.putString("fistName", userProfile.getFirstName());
        editor.putString("lastName", userProfile.getLastName());
        editor.putString("avatar", userProfile.getAvatar());
        editor.putString("userType", userProfile.getUserType());
        editor.putInt("point", userProfile.getPoint());

        editor.apply();
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        MySharedPreferences mySharedPreferences = new MySharedPreferences(context);
        SharedPreferences.Editor editor = mySharedPreferences.getEditor();
        editor.putString("username", userProfile.getUsername());
        editor.putString("mail", userProfile.getMail());
        editor.putString("fistName", userProfile.getFirstName());
        editor.putString("lastName", userProfile.getLastName());
        editor.putString("avatar", userProfile.getAvatar());
        editor.putString("userType", userProfile.getUserType());
        editor.putInt("point", userProfile.getPoint());
        editor.apply();
    }

    public String getUserId() {
        return user_id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getBearerAccessToken(){
        return "Bearer " + accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void clearCredential() {
        this.user_id = "";
        this.accessToken= "";
        this.refreshToken = "";
        this.userProfile = null;
        MySharedPreferences mySharedPreferences = new MySharedPreferences(context);
        SharedPreferences.Editor editor = mySharedPreferences.getEditor();
        editor.clear();
        editor.apply();
    }
}
