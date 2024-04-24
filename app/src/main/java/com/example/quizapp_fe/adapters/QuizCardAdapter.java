package com.example.quizapp_fe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.interfaces.QuizCard;

import java.util.ArrayList;

public class QuizCardAdapter extends RecyclerView.Adapter<QuizCardViewHolder> {
    Context context;
    ArrayList<QuizCard> dataList;
    public QuizCardAdapter(Context context, ArrayList<QuizCard> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public QuizCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_quiz_card, parent, false);
        return new QuizCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizCardViewHolder holder, int position) {
        QuizCard quizCard = dataList.get(position);
        holder.bind(quizCard);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
