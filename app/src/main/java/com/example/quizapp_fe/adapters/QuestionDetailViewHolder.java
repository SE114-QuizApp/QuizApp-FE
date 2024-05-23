package com.example.quizapp_fe.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.entities.Answer;
import com.example.quizapp_fe.entities.Question;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class QuestionDetailViewHolder extends RecyclerView.ViewHolder {
    TextView questionDetailNumericalOrderTextView;
    TextView questionDetailContentTextView;
    TextView questionDetailOptionQuestionTextView;
    TextView questionDetailAnswerTime;
    RecyclerView questionDetailAnswerListRecyclerView;
    AnswerDetailAdapter answerDetailAdapter;
    ArrayList<Answer> answerArrayList;

    public QuestionDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        questionDetailNumericalOrderTextView = itemView.findViewById(R.id.questionDetailNumericalOrderTextView);
        questionDetailContentTextView = itemView.findViewById(R.id.questionDetailContentTextView);
        questionDetailOptionQuestionTextView = itemView.findViewById(R.id.questionDetailOptionQuestionTextView);
        questionDetailAnswerTime = itemView.findViewById(R.id.questionDetailAnswerTime);
        questionDetailAnswerListRecyclerView = itemView.findViewById(R.id.questionDetailAnswerListRecyclerView);
        answerArrayList = new ArrayList<>();
    }

    public void bind(Question question, Context context) {
        questionDetailNumericalOrderTextView.setText(Integer.toString(question.getQuestionIndex()));
        questionDetailContentTextView.setText(question.getContent());
        questionDetailOptionQuestionTextView.setText(question.getOptionQuestion() + " Option");
        questionDetailAnswerTime.setText(Integer.toString(question.getAnswerTime()) + " Seconds");

        ArrayList<Answer> answersFromQuestion = new ArrayList<>(question.getAnswerList());

        if(!answerArrayList.isEmpty()) {
            answerArrayList.clear();
        }
        for (int i = 0; i < answersFromQuestion.size(); i++) {
            Answer answer = answersFromQuestion.get(i);
            if (answer.getBody().isEmpty()) {
                continue;
            }
            answerArrayList.add(answer);
        }


        answerDetailAdapter = new AnswerDetailAdapter(context, answerArrayList);
        questionDetailAnswerListRecyclerView.setAdapter(answerDetailAdapter);
        questionDetailAnswerListRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
}
