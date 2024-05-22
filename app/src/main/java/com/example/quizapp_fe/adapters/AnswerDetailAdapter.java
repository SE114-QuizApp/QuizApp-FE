package com.example.quizapp_fe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.entities.Answer;

import java.util.ArrayList;

public class AnswerDetailAdapter extends RecyclerView.Adapter<AnswerDetailViewHolder> {
    Context context;
    ArrayList<Answer> dataList;

    public AnswerDetailAdapter(Context context, ArrayList<Answer> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public AnswerDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_answer_detail, parent, false);
        return new AnswerDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerDetailViewHolder holder, int position) {
        Answer answer = dataList.get(position);
        holder.bind(answer, context);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
