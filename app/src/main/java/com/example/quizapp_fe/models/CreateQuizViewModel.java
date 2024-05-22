package com.example.quizapp_fe.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quizapp_fe.entities.Question;
import com.example.quizapp_fe.entities.Quiz;

import java.util.ArrayList;

// class to store the quiz data
public class CreateQuizViewModel extends ViewModel {
    private final MutableLiveData<Quiz> currentQuiz = new MutableLiveData<>();

    public LiveData<Quiz> getQuiz() {
        return currentQuiz;
    }

    public void setQuiz(Quiz currentQuiz) {
        this.currentQuiz.setValue(currentQuiz);
    }

    public void addQuestion(Question question) {
        Quiz quiz = currentQuiz.getValue();
        quiz.getQuestionList().add(question);
        currentQuiz.setValue(quiz);
    }

    public void updateQuestion(int index, Question question) {
        Quiz quiz = currentQuiz.getValue();
        quiz.getQuestionList().set(index, question);
        currentQuiz.setValue(quiz);
    }

    public void clearQuiz() {
        // check if quiz is not null, then clear quiz
        if (currentQuiz.getValue() != null) {
            this.currentQuiz.setValue(null);
        }
    }
}