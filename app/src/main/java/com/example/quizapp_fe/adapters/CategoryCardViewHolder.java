package com.example.quizapp_fe.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.DiscoverySearchActivity;
import com.example.quizapp_fe.fragments.QuizFragment;
import com.example.quizapp_fe.interfaces.CategoryCard;

public class CategoryCardViewHolder extends RecyclerView.ViewHolder {
    ImageView categoryImageView;
    TextView categoryTitleTextView;
    TextView categoryContentTextView;
    LinearLayout categoryItemLinearLayout;
    FragmentManager fragmentManager;
    public CategoryCardViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryImageView = itemView.findViewById(R.id.categoryItemImageView);
        categoryTitleTextView = itemView.findViewById(R.id.categoryItemTitleTextView);
        categoryContentTextView = itemView.findViewById(R.id.categoryItemContentTextView);
        categoryItemLinearLayout = itemView.findViewById(R.id.categoryItemLinearLayout);
        fragmentManager = ((AppCompatActivity) itemView.getContext()).getSupportFragmentManager();
    }
    public void bind(CategoryCard categoryCard, Context context) {
        categoryImageView.setImageResource(categoryCard.getCategoryCardImage());
        categoryTitleTextView.setText(categoryCard.getCategoryCardTitle());
        categoryContentTextView.setText(categoryCard.getCategoryCardContent());
        categoryItemLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.animation_normal);
                categoryItemLinearLayout.startAnimation(animation);
                Intent intent = new Intent(context, DiscoverySearchActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
