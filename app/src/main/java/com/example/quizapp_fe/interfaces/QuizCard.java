package com.example.quizapp_fe.interfaces;

import android.widget.ImageView;
import android.widget.TextView;

public class QuizCard {
    private int quizCardImage;
    private String titleText;
    private String creatorText;
    private String statusText;

    public QuizCard(int quizCardImage, String titleText, String creatorText, String statusText) {
        this.quizCardImage = quizCardImage;
        this.titleText = titleText;
        this.creatorText = creatorText;
        this.statusText = statusText;
    }

    public int getQuizCardImage() {
        return quizCardImage;
    }

    public String getTitleText() {
        return titleText;
    }

    public String getCreatorText() {
        return creatorText;
    }

    public String getStatusText() {
        return statusText;
    }
}