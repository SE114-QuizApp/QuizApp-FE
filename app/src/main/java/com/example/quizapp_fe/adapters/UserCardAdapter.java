package com.example.quizapp_fe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.interfaces.UserCard;

import java.util.ArrayList;

public class UserCardAdapter extends RecyclerView.Adapter<UserCardViewHolder> {
    Context context;
    ArrayList<UserCard> dataList;

    public UserCardAdapter(Context context, ArrayList<UserCard> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public UserCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_user_card, parent, false);
        UserCardViewHolder viewHolder = new UserCardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserCardViewHolder holder, int position) {
        UserCard userCard = dataList.get(position);
        holder.bind(userCard, context);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
