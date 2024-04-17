package com.example.quizapp_fe.entities;

public class Category {
    private String name;
    private String _id;

    public Category(String name, String _id) {
        this.name = name;
        this._id = _id;
    }
    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
