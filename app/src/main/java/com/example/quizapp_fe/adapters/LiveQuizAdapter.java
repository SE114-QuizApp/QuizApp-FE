package com.example.quizapp_fe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.dialogs.DeleteConfirmationDialog;
import com.example.quizapp_fe.interfaces.LiveQuizCard;

import java.util.ArrayList;

public class LiveQuizAdapter extends RecyclerView.Adapter<LiveQuizViewHolder> {
    Context context;
    ArrayList<LiveQuizCard> liveQuizList;

    public LiveQuizAdapter(Context context, ArrayList<LiveQuizCard> liveQuizList) {
        this.context = context;
        this.liveQuizList = liveQuizList;
    }

    public void setData(ArrayList<LiveQuizCard> list) {
        this.liveQuizList = list;
        notifyDataSetChanged();
        
    }

    @NonNull
    @Override
    public LiveQuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyler_item_live_quiz_row, parent, false);
        return new LiveQuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveQuizViewHolder holder, int position) {
        LiveQuizCard liveQuizCard = liveQuizList.get(position);
        holder.bind(liveQuizCard, context, liveQuizList, new DeleteConfirmationDialog.OnDeleteClickListener() {
            @Override
            public void onDelete(int position) {
                notifyDataSetChanged();
            }
        });
        };
    @Override
    public int getItemCount() {
        return liveQuizList.size();
    };

}
