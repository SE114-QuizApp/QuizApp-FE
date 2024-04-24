package com.example.quizapp_fe.entities;

import java.io.Serializable;

public class UserRank extends UserProfile implements Serializable {
    private Integer rank;

    public UserRank(String _id, String userName, String mail, String firstName, String lastName, String avatar, String userType, Integer point, Integer rank) {
        super(_id, userName, mail, firstName, lastName, avatar, userType, point);
        this.rank = rank;
    }

    public UserRank(UserProfile userProfile, Integer rank) {
        super(userProfile.getId(), userProfile.getUsername(), userProfile.getMail(), userProfile.getFirstName(), userProfile.getLastName(), userProfile.getAvatar(), userProfile.getUserType(), userProfile.getPoint());
        this.rank = rank;

    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

}
