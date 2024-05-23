package com.example.quizapp_fe.entities;

public class Category {
    private String _id;
    private String name;

    public Category(String _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
