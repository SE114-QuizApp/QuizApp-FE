package com.example.quizapp_fe.models;

import android.widget.LinearLayout;

import com.example.quizapp_fe.entities.Answer;
import com.example.quizapp_fe.entities.Question;

import java.io.Serializable;
import java.util.ArrayList;

public class UserAnswers implements Serializable {
    private ArrayList<Question> userQuestionList;
    private int totalPoints;

    public UserAnswers(ArrayList<Question> userQuestionList, int totalPoints) {
        this.userQuestionList = userQuestionList;
        this.totalPoints = totalPoints;
    }

    public ArrayList<Question> getUserQuestionList() {
        return userQuestionList;
    }

    public int getTotalPoints() {
        return totalPoints;
    }
}
