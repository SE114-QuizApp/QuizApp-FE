package com.example.quizapp_fe.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private String _id;
    private String quizId;
    private ArrayList<String> tags;
    private String questionType;
    private String optionQuestion;
    private boolean isPublic;
    private String pointType;
    private int answerTime;
    private String backgroundImage;
    private String content;
    private ArrayList<Answer> answerList;
    private int questionIndex;
    private int maxCorrectAnswer;
    private int correctAnswerCount;
    private ArrayList<String> answerCorrect;

    public String getContent() {
        return content;
    }

    public int getAnswerTime() {
        return answerTime;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public ArrayList<Answer> getAnswerList() {
        return answerList;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public String getOptionQuestion() {
        return optionQuestion;
    }

    public int getMaxCorrectAnswer() {
        return maxCorrectAnswer;
    }

    public int getCorrectAnswerCount() {
        return correctAnswerCount;
    }
}
