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
    private  int correctAnswerCount;
    private ArrayList<Answer> answerCorrect;
    private Category category;
    private Grade grade;

    public Quiz()
    {

    }

    public Quiz(String _id, String name, String description, String backgroundImage, int pointsPerQuestion,
                int numberOfQuestions, boolean isPublic, ArrayList<String> tags, ArrayList<String> likesCount,
                boolean isDraft, ArrayList<String> comments, ArrayList<Question> questionList, String createdAt,
                String updatedAt, Creator creator, int correctAnswerCount, ArrayList<Answer> answerCorrect, Category category, Grade grade) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.backgroundImage = backgroundImage;
        this.pointsPerQuestion = pointsPerQuestion;
        this.numberOfQuestions = numberOfQuestions;
        this.isPublic = isPublic;
        this.tags = tags;
        this.likesCount = likesCount;
        this.isDraft = isDraft;
        this.comments = comments;
        this.questionList = questionList;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.creator = creator;
        this.correctAnswerCount = correctAnswerCount;
        this.answerCorrect = answerCorrect;
        this.category = category;
        this.grade = grade;
    }

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }


    public int getPointsPerQuestion() {
        return pointsPerQuestion;
    }

    public String get_id() {
        return _id;
    }
    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public String getTotalPoints() {
        int totalPoints = this.pointsPerQuestion * this.numberOfQuestions;
        return Integer.toString(totalPoints);
    }

    public Creator getCreator() {
        return creator;
    }
}
