package com.example.quizapp_fe.interfaces;

public class LiveQuizCard {
    private int liveQuizCardImage;
    private String liveQuizCardTitle;
    private String liveQuizCardSubTitle;

    public LiveQuizCard(int liveQuizCardImage, String liveQuizCardTitle, String liveQuizCardSubTitle) {
        this.liveQuizCardImage = liveQuizCardImage;
        this.liveQuizCardTitle = liveQuizCardTitle;
        this.liveQuizCardSubTitle = liveQuizCardSubTitle;
    }

    public int getLiveQuizCardImage() {
        return liveQuizCardImage;
    }

    public String getLiveQuizCardTitle() {
        return liveQuizCardTitle;
    }

    public String getLiveQuizCardSubTitle() {
        return liveQuizCardSubTitle;
    }
}
