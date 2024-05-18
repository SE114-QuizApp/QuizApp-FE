package com.example.quizapp_fe.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.interfaces.QuizCard;

public class QuizCardViewHolder extends RecyclerView.ViewHolder {

    ImageView quizCardItemImageView;
    TextView quizCardItemTitle;
    TextView quizCardItemCreator;
    TextView quizCardItemStatus;
    ImageView quizCardForwardArrow;

    public QuizCardViewHolder(@NonNull View itemView) {
        super(itemView);
        quizCardItemImageView = itemView.findViewById(R.id.quizCardItemImageView);
        quizCardItemTitle = itemView.findViewById(R.id.quizCardItemTitleTextView);
        quizCardItemCreator = itemView.findViewById(R.id.quizCardItemCreatorTextView);
        quizCardItemStatus = itemView.findViewById(R.id.quizCardItemStatusTextView);
        quizCardForwardArrow = itemView.findViewById(R.id.quizCardItemForwardArrow);
    }

    public void bind(QuizCard quizCard, Context context) {
        quizCardItemImageView.setImageResource(quizCard.getQuizCardImage());
        quizCardItemTitle.setText(quizCard.getTitleText());
        quizCardItemCreator.setText(quizCard.getCreatorText());
        quizCardItemStatus.setText(quizCard.getStatusText());

        quizCardForwardArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.animation_normal);
                quizCardForwardArrow.startAnimation(animation);
            }
        });
    }

}
