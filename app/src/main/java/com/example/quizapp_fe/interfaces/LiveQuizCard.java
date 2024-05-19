package com.example.quizapp_fe.interfaces;

public class LiveQuizCard {
    private String liveQuizCardImage;
    private String liveQuizCardTitle;
    private String liveQuizCardSubTitle;

    public LiveQuizCard(String liveQuizCardImage, String liveQuizCardTitle, String liveQuizCardSubTitle) {
        this.liveQuizCardImage = liveQuizCardImage;
        this.liveQuizCardTitle = liveQuizCardTitle;
        this.liveQuizCardSubTitle = liveQuizCardSubTitle;
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

    public void setLiveQuizCardImage(String liveQuizCardImage) {
        this.liveQuizCardImage = liveQuizCardImage;
    }

    public void setLiveQuizCardTitle(String liveQuizCardTitle) {
        this.liveQuizCardTitle = liveQuizCardTitle;
    }

    public void setLiveQuizCardSubTitle(String liveQuizCardSubTitle) {
        this.liveQuizCardSubTitle = liveQuizCardSubTitle;
    }
}
