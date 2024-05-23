package com.example.quizapp_fe.adapters;

import android.content.Context;

import android.content.Intent;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.QuizDetailActivity;
import com.example.quizapp_fe.interfaces.QuizCard;

public class QuizCardViewHolder extends RecyclerView.ViewHolder {

    ImageView quizCardItemImageView;
    TextView quizCardItemTitle;
    TextView quizCardItemCreator;
    TextView quizCardCategory;
    ImageView quizCardForwardArrow;
    ConstraintLayout quizcardConstraintLayout;

    public QuizCardViewHolder(@NonNull View itemView) {
        super(itemView);
        quizCardItemImageView = itemView.findViewById(R.id.quizCardItemImageView);
        quizCardItemTitle = itemView.findViewById(R.id.quizCardItemTitleTextView);
        quizCardItemCreator = itemView.findViewById(R.id.quizCardItemCreatorTextView);
        quizCardCategory = itemView.findViewById(R.id.quizCardItemCategoryTextView);
        quizCardForwardArrow = itemView.findViewById(R.id.quizCardItemForwardArrow);
        quizcardConstraintLayout = itemView.findViewById(R.id.quizcardConstraintLayout);
    }

    public void bind(QuizCard quizCard, Context context) {
        Glide.with(context)
                     .asBitmap()
                     .load(quizCard.getQuizCardImage())
                     .error(R.drawable.img_sample_quiz)
                     .into(quizCardItemImageView);
        quizCardItemTitle.setText(quizCard.getTitleText());
        quizCardItemCreator.setText(quizCard.getCreatorText());
        quizCardCategory.setText(quizCard.getCategoryText());

        quizcardConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.animation_fade_in);
                quizcardConstraintLayout.startAnimation(animation);
                Intent intent = new Intent(context, QuizDetailActivity.class);
                intent.putExtra("quizId", quizCard.getQuizCardId());
                context.startActivity(intent);
            }
        });
    }

}
