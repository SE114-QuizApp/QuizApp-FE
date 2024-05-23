package com.example.quizapp_fe.entities;

import java.io.Serializable;

public class Grade implements Serializable {
    private String name;
    private String _id;

    public Grade(String name, String _id) {
        this.name = name;
        this._id = _id;
    }

    public Grade(String name) {
        this.name = name;
    }

    public Grade() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
