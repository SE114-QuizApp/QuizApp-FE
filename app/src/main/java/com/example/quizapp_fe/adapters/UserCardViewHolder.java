package com.example.quizapp_fe.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    public void bind(@NonNull UserCard userCard, Context context){
        Glide.with(context)
             .asBitmap()
                     .load(userCard.getUserCardImage())
                     .error(R.drawable.img_lookup)
                     .into(userCardItemImageView);
        userCardItemName.setText(userCard.getUserCardName());
        userCardItemEmail.setText(userCard.getUserCardEmail());
    }
}
