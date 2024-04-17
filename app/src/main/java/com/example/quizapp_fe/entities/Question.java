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

}
