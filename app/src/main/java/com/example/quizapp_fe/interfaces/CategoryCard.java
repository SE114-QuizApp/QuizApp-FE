package com.example.quizapp_fe.interfaces;

import java.io.Serializable;

public class CategoryCard implements Serializable {
    private int categoryCardImage;
    private String categoryCardTitle;
    private String categoryCardContent;

    // flag to indicate whether the category is selected
    private boolean isSelected;

    public CategoryCard(int categoryCardImage, String categoryCardTitle, String categoryCardContent) {
        this.categoryCardImage = categoryCardImage;
        this.categoryCardTitle = categoryCardTitle;
        this.categoryCardContent = categoryCardContent;
    }

    public CategoryCard(String categoryCardTitle, int categoryCardImage) {
        this.categoryCardTitle = categoryCardTitle;
        this.categoryCardImage = categoryCardImage;
        this.isSelected = false;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
