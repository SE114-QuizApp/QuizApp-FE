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

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public Grade getGrade() {
        return grade;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setPointsPerQuestion(int pointsPerQuestion) {
        this.pointsPerQuestion = pointsPerQuestion;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void setDraft(boolean draft) {
        this.isDraft = isDraft;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public boolean isDraft() {
        return isDraft;
    }

    public ArrayList<String> getLikesCount() {
        return likesCount;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}