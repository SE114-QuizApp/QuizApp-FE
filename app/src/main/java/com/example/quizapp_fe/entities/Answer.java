package com.example.quizapp_fe.entities;

import java.io.Serializable;

public class Answer implements Serializable {
    private String name;
    private String body;
    private boolean isCorrect;
    private String _id;

    public Answer(String name, String body, boolean isCorrect, String _id) {
        this.name = name;
        this.body = body;
        this.isCorrect = isCorrect;
        this._id = _id;
    }

    public Answer(String name, String body, boolean isCorrect) {
        this.name = name;
        this.body = body;
        this.isCorrect = isCorrect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
