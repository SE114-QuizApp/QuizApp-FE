package com.example.quizapp_fe.interfaces;

public class CategoryCard {
    private int categoryCardImage;
    private String categoryCardTitle;
    private String getCategoryCardContent;

    public CategoryCard(int categoryCardImage, String categoryCardTitle, String getCategoryCardContent) {
        this.categoryCardImage = categoryCardImage;
        this.categoryCardTitle = categoryCardTitle;
        this.getCategoryCardContent = getCategoryCardContent;
    }

    public int getCategoryCardImage() {
        return categoryCardImage;
    }

    public String getCategoryCardTitle() {
        return categoryCardTitle;
    }

    public String getGetCategoryCardContent() {
        return getCategoryCardContent;
    }
}
