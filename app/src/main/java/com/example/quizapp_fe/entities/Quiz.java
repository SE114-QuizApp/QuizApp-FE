package com.example.quizapp_fe.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Quiz implements Serializable {

    private String _id;
    private String name;
    private String description;
    private String backgroundImage;
    private int pointsPerQuestion;
    private int numberOfQuestions;
    private boolean isPublic;
    private ArrayList<String> tags;
    private ArrayList<String> likesCount;
    private boolean isDraft;
    private ArrayList<String> comments;
    private ArrayList<Question> questionList;
    private String createdAt;
    private String updatedAt;
    private Creator creator;
    private Category category;
    private Grade grade;


}
