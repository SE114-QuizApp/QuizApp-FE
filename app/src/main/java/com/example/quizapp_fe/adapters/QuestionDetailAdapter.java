package com.example.quizapp_fe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.entities.Question;

import java.util.ArrayList;

public class QuestionDetailAdapter extends RecyclerView.Adapter<QuestionDetailViewHolder> {
    Context context;
    ArrayList<Question> dataList;

    public QuestionDetailAdapter(Context context, ArrayList<Question> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public QuestionDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_question_detail, parent, false);
        return new QuestionDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionDetailViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
