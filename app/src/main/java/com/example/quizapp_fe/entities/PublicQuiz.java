package com.example.quizapp_fe.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class PublicQuiz implements Serializable {
    int currentPage;
    int pageSize;
    int numberOfPages;
    ArrayList<Quiz> data;

    public PublicQuiz(int currentPage, int pageSize, int numberOfPages, ArrayList<Quiz> data) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.numberOfPages = numberOfPages;
        this.data = data;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public ArrayList<Quiz> getData() {
        return data;
    }
}
