package com.example.quizapp_fe.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.entities.Answer;

public class AnswerDetailViewHolder extends RecyclerView.ViewHolder {

    TextView answerDetailNameTextView;
    TextView answerDetailBodyTextView;

    public AnswerDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        answerDetailNameTextView = itemView.findViewById(R.id.answerDetailNameTextView);
        answerDetailBodyTextView = itemView.findViewById(R.id.answerDetailBodyTextView);
    }

    void bind(Answer answer, Context context) {
        answerDetailNameTextView.setText(answer.getName());
        answerDetailBodyTextView.setText(answer.getBody());
    }
}
