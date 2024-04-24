package com.example.quizapp_fe.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.interfaces.UserCard;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserCardViewHolder extends RecyclerView.ViewHolder {
    ImageView userCardItemImageView;
    TextView userCardItemName;
    TextView userCardItemEmail;
    public UserCardViewHolder(@NonNull View itemView) {
        super(itemView);
        userCardItemImageView = itemView.findViewById(R.id.userCardItemImageView);
        userCardItemName = itemView.findViewById(R.id.userCardItemNameTextView);
        userCardItemEmail = itemView.findViewById(R.id.userCardItemEmailTextView);
    }
    public void bind(UserCard userCard){
        userCardItemImageView.setImageResource(userCard.getUserCardImage());
        userCardItemName.setText(userCard.getUserCardName());
        userCardItemEmail.setText(userCard.getUserCardEmail());
    }
}
