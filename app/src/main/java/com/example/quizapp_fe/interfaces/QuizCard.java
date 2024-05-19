package com.example.quizapp_fe.interfaces;

import android.widget.ImageView;
import android.widget.TextView;

public class QuizCard {
    private String quizCardId;
    private String quizCardImage;
    private String titleText;
    private String creatorText;
    private String category;

    public QuizCard(String quizCardId, String quizCardImage, String titleText, String creatorText, String category) {
        this.quizCardId = quizCardId;
        this.quizCardImage = quizCardImage;
        this.titleText = titleText;
        this.creatorText = creatorText;
        this.category = category;
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
}
