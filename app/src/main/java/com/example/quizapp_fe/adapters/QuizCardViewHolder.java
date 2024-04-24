package com.example.quizapp_fe.adapters;

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
    }

    public void bind(QuizCard quizCard) {
        quizCardItemImageView.setImageResource(quizCard.getQuizCardImage());
        quizCardItemTitle.setText(quizCard.getTitleText());
        quizCardItemCreator.setText(quizCard.getCreatorText());
        quizCardItemStatus.setText(quizCard.getStatusText());
    }

}
