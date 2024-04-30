package com.example.quizapp_fe.models;

import android.widget.LinearLayout;

import com.example.quizapp_fe.entities.Answer;
import com.example.quizapp_fe.entities.Question;

import java.io.Serializable;
import java.util.ArrayList;

public class UserAnswers implements Serializable {
    private ArrayList<Answer> userAnswersList;
    private ArrayList<Question> questionsList;

    private int totalPoints;

    public UserAnswers(ArrayList<Answer> userAnswers, ArrayList<Question> questionsList, int totalPoints) {
        this.userAnswersList = userAnswers;
        this.questionsList = questionsList;
        this.totalPoints = totalPoints;
    }

    public ArrayList<Answer> getUserAnswersList() {
        return userAnswersList;
    }

    public ArrayList<Question> getQuestionsList() {
        return questionsList;
    }

    public int getTotalPoints() {
        return totalPoints;
    }
}
