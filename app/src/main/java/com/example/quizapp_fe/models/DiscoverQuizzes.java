package com.example.quizapp_fe.models;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.quizapp_fe.entities.DiscoverQuiz;
import com.example.quizapp_fe.entities.Quiz;

public class DiscoverQuizzes implements Serializable {
    private ArrayList<DiscoverQuiz> English;
    private ArrayList<DiscoverQuiz> Math;
    private ArrayList<DiscoverQuiz> Computer;

    public DiscoverQuizzes(ArrayList<DiscoverQuiz> english, ArrayList<DiscoverQuiz> math, ArrayList<DiscoverQuiz> computer) {
        English = english;
        Math = math;
        Computer = computer;
    }

    public ArrayList<DiscoverQuiz> getEnglish() {
        return English;
    }

    public void setEnglish(ArrayList<DiscoverQuiz> english) {
        English = english;
    }

    public ArrayList<DiscoverQuiz> getMath() {
        return Math;
    }

    public void setMath(ArrayList<DiscoverQuiz> math) {
        Math = math;
    }

    public ArrayList<DiscoverQuiz> getComputer() {
        return Computer;
    }

    public void setComputer(ArrayList<DiscoverQuiz> computer) {
        Computer = computer;
    }
}
