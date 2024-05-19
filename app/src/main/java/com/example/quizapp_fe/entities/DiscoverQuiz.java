package com.example.quizapp_fe.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class DiscoverQuiz implements Serializable {
    private String _id;
    private String name;
    private DiscoverCreator creator;
    private String description;
    private String backgroundImage;
    private boolean isDraft;
    private boolean isPublic;
    private ArrayList<String> tags;
    private int numberOfQuestions;
    private int pointsPerQuestion;
    private ArrayList<String> likesCount;
    private ArrayList<String> questionList;
    private String createdAt;
    private String updatedAt;
    private Category category;
    private String grade;
    private int __v;

    public DiscoverQuiz(String _id, String name, DiscoverCreator creator, String description, String backgroundImage, boolean isDraft, boolean isPublic, ArrayList<String> tags, int numberOfQuestions, int pointsPerQuestion, ArrayList<String> likesCount, ArrayList<String> questionList, String createdAt, String updatedAt, Category category, String grade, int __v) {
        this._id = _id;
        this.name = name;
        this.creator = creator;
        this.description = description;
        this.backgroundImage = backgroundImage;
        this.isDraft = isDraft;
        this.isPublic = isPublic;
        this.tags = tags;
        this.numberOfQuestions = numberOfQuestions;
        this.pointsPerQuestion = pointsPerQuestion;
        this.likesCount = likesCount;
        this.questionList = questionList;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = category;
        this.grade = grade;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DiscoverCreator getCreator() {
        return creator;
    }

    public void setCreator(DiscoverCreator creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public boolean isDraft() {
        return isDraft;
    }

    public void setDraft(boolean draft) {
        isDraft = draft;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getPointsPerQuestion() {
        return pointsPerQuestion;
    }

    public void setPointsPerQuestion(int pointsPerQuestion) {
        this.pointsPerQuestion = pointsPerQuestion;
    }

    public ArrayList<String> getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(ArrayList<String> likesCount) {
        this.likesCount = likesCount;
    }

    public ArrayList<String> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<String> questionList) {
        this.questionList = questionList;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
