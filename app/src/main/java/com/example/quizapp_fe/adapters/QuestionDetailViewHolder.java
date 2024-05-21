package com.example.quizapp_fe.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.entities.Question;

import org.w3c.dom.Text;

public class QuestionDetailViewHolder extends RecyclerView.ViewHolder {
    TextView questionDetailNumericalOrderTextView;
    TextView questionDetailContentTextView;
    TextView questionDetailOptionQuestionTextView;
    TextView questionDetailAnswerTime;

    public QuestionDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        questionDetailNumericalOrderTextView = itemView.findViewById(R.id.questionDetailNumericalOrderTextView);
        questionDetailContentTextView = itemView.findViewById(R.id.questionDetailContentTextView);
        questionDetailOptionQuestionTextView = itemView.findViewById(R.id.questionDetailOptionQuestionTextView);
        questionDetailAnswerTime = itemView.findViewById(R.id.questionDetailAnswerTime);
    }

    public void bind(Question question, Context context) {

    }
}
