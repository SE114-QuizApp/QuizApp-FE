package com.example.quizapp_fe.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.interfaces.CategoryCard;

public class CategoryCardViewHolder extends RecyclerView.ViewHolder {
    ImageView categoryImageView;
    TextView categoryTitleTextView;
    TextView categoryContentTextView;
    public CategoryCardViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryImageView = itemView.findViewById(R.id.categoryItemImageView);
        categoryTitleTextView = itemView.findViewById(R.id.categoryItemTitleTextView);
        categoryContentTextView = itemView.findViewById(R.id.categoryItemContentTextView);
    }
    public void bind(CategoryCard categoryCard) {
        categoryImageView.setImageResource(categoryCard.getCategoryCardImage());
        categoryTitleTextView.setText(categoryCard.getCategoryCardTitle());
        categoryContentTextView.setText(categoryCard.getCategoryCardContent());
    }
}
