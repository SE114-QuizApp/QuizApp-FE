package com.example.quizapp_fe.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import com.example.quizapp_fe.entities.DiscoverQuiz;
import com.example.quizapp_fe.entities.Quiz;

public class DiscoverQuizzes implements Serializable {
    private Map<String, ArrayList<DiscoverQuiz>> quizzes;

    public DiscoverQuizzes(Map<String, ArrayList<DiscoverQuiz>> quizzes) {
        this.quizzes = quizzes;
    }

    public Map<String, ArrayList<DiscoverQuiz>> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Map<String, ArrayList<DiscoverQuiz>> quizzes) {
        this.quizzes = quizzes;
    }
}
