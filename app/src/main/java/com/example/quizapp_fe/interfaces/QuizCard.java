package com.example.quizapp_fe.interfaces;

import android.widget.ImageView;
import android.widget.TextView;

public class QuizCard {
    private String quizCardId;
    private String quizCardImage;
    private String titleText;
    private String creatorText;
    private String category;

    private int numOfQuestions;

    public QuizCard(String quizCardId, String quizCardImage, String titleText, String creatorText, String category) {
        this.quizCardId = quizCardId;
        this.quizCardImage = quizCardImage;
        this.titleText = titleText;
        this.creatorText = creatorText;
        this.category = category;
    }

    public QuizCard(String quizCardId, String quizCardImage, String titleText, String creatorText, String category, int numOfQuestions) {
        this.quizCardId = quizCardId;
        this.quizCardImage = quizCardImage;
        this.titleText = titleText;
        this.creatorText = creatorText;
        this.category = category;
        this.numOfQuestions = numOfQuestions;
    }

    public String getQuizCardId() { return quizCardId; }
    public String getQuizCardImage() { return quizCardImage; }

    public String getTitleText() {
        return titleText;
    }

    public String getCreatorText() {
        return creatorText;
    }

    public String getCategoryText() {
        return category;
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }
}
