package com.example.quizapp_fe.adapters;

import android.content.Context;
<<<<<<< HEAD
import android.content.Intent;
=======
>>>>>>> 49620168dfca81259a36e43207b9880a78adfbc0
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.QuizDetailActivity;
import com.example.quizapp_fe.interfaces.QuizCard;

public class QuizCardViewHolder extends RecyclerView.ViewHolder {

    ImageView quizCardItemImageView;
    TextView quizCardItemTitle;
    TextView quizCardItemCreator;
    TextView quizCardCategory;
    ImageView quizCardForwardArrow;

    public QuizCardViewHolder(@NonNull View itemView) {
        super(itemView);
        quizCardItemImageView = itemView.findViewById(R.id.quizCardItemImageView);
        quizCardItemTitle = itemView.findViewById(R.id.quizCardItemTitleTextView);
        quizCardItemCreator = itemView.findViewById(R.id.quizCardItemCreatorTextView);
<<<<<<< HEAD
        quizCardCategory = itemView.findViewById(R.id.quizCardItemCategoryTextView);
=======
        quizCardItemStatus = itemView.findViewById(R.id.quizCardItemStatusTextView);
>>>>>>> 49620168dfca81259a36e43207b9880a78adfbc0
        quizCardForwardArrow = itemView.findViewById(R.id.quizCardItemForwardArrow);
    }

    public void bind(QuizCard quizCard, Context context) {
        quizCardItemImageView.setImageResource(quizCard.getQuizCardImage());
        quizCardItemTitle.setText(quizCard.getTitleText());
        quizCardItemCreator.setText(quizCard.getCreatorText());
<<<<<<< HEAD
        quizCardCategory.setText(quizCard.getCategoryText());
=======
        quizCardItemStatus.setText(quizCard.getStatusText());
>>>>>>> 49620168dfca81259a36e43207b9880a78adfbc0

        quizCardForwardArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.animation_normal);
                quizCardForwardArrow.startAnimation(animation);
<<<<<<< HEAD
                Intent intent = new Intent(context, QuizDetailActivity.class);
                context.startActivity(intent);
=======
>>>>>>> 49620168dfca81259a36e43207b9880a78adfbc0
            }
        });
    }

}
