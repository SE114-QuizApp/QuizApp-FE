package com.example.quizapp_fe.interfaces;

public class LiveQuizCard {
    private String liveQuizCardImage;
    private String liveQuizCardTitle;
    private String liveQuizCardSubTitle;
    private String liveQuizCardId;
    private int liveQuizCardNumOfQuestions;

    public LiveQuizCard(String liveQuizCardImage, String liveQuizCardTitle, String liveQuizCardId) {
        this.liveQuizCardImage = liveQuizCardImage;
        this.liveQuizCardTitle = liveQuizCardTitle;
        this.liveQuizCardId = liveQuizCardId;
        this.liveQuizCardSubTitle = "";
    }

    public LiveQuizCard(String liveQuizCardImage, String liveQuizCardTitle, String liveQuizCardSubTitle, String liveQuizCardId) {
        this.liveQuizCardImage = liveQuizCardImage;
        this.liveQuizCardTitle = liveQuizCardTitle;
        this.liveQuizCardSubTitle = liveQuizCardSubTitle;
        this.liveQuizCardId = liveQuizCardId;
    }

    public LiveQuizCard(String liveQuizCardImage, String liveQuizCardTitle, String liveQuizCardSubTitle, String liveQuizCardId, int liveQuizCardNumOfQuestions) {
        this.liveQuizCardImage = liveQuizCardImage;
        this.liveQuizCardTitle = liveQuizCardTitle;
        this.liveQuizCardSubTitle = liveQuizCardSubTitle;
        this.liveQuizCardId = liveQuizCardId;
        this.liveQuizCardNumOfQuestions = liveQuizCardNumOfQuestions;
    }

    public String getLiveQuizCardImage() {
        return liveQuizCardImage;
    }

    public String getLiveQuizCardTitle() {
        return liveQuizCardTitle;
    }

    public String getLiveQuizCardSubTitle() {
        return liveQuizCardSubTitle;
    }

    public String getLiveQuizCardId() {
        return liveQuizCardId;
    }

    public void setLiveQuizCardImage(String liveQuizCardImage) {
        this.liveQuizCardImage = liveQuizCardImage;
    }

    public void setLiveQuizCardTitle(String liveQuizCardTitle) {
        this.liveQuizCardTitle = liveQuizCardTitle;
    }

    public void setLiveQuizCardSubTitle(String liveQuizCardSubTitle) {
        this.liveQuizCardSubTitle = liveQuizCardSubTitle;
    }

    public void setLiveQuizCardId(String liveQuizCardId) {
        this.liveQuizCardId = liveQuizCardId;
    }

    public int getLiveQuizCardNumOfQuestions() {
        return liveQuizCardNumOfQuestions;
    }

    public void setLiveQuizCardNumOfQuestions(int liveQuizCardNumOfQuestions) {
        this.liveQuizCardNumOfQuestions = liveQuizCardNumOfQuestions;
    }
}
