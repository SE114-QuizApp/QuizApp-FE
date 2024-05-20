package com.example.quizapp_fe.interfaces;

public class CategoryCard {
    private int categoryCardImage;
    private String categoryCardTitle;
    private String categoryCardContent;

    public CategoryCard(int categoryCardImage, String categoryCardTitle, String categoryCardContent) {
        this.categoryCardImage = categoryCardImage;
        this.categoryCardTitle = categoryCardTitle;
        this.categoryCardContent = categoryCardContent;
    }

    public int getCategoryCardImage() {
        return categoryCardImage;
    }

    public String getCategoryCardTitle() {
        return categoryCardTitle;
    }

    public String getCategoryCardContent() {
        return categoryCardContent;
    }
}
