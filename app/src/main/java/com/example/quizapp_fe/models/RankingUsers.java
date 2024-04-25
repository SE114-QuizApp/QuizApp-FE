package com.example.quizapp_fe.models;

import com.example.quizapp_fe.entities.UserProfile;
import com.example.quizapp_fe.entities.UserRank;

import java.util.ArrayList;

public class RankingUsers {
    ArrayList<UserRank> users = new ArrayList<>();
    UserRank currentUser;

    static RankingUsers instance;

    public RankingUsers(ArrayList<UserRank> users, UserRank currentUser) {
        this.users = users;
        this.currentUser = currentUser;
    }

    public RankingUsers() {
    }

    public static RankingUsers getInstance() {
        if (instance == null) {
            instance = new RankingUsers();
        }
        return instance;
    }

    public ArrayList<UserRank> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserRank> users) {
        this.users = users;
    }

    public UserRank getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserRank currentUser) {
        this.currentUser = currentUser;
    }
}
