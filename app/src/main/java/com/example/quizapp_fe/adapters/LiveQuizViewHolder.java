package com.example.quizapp_fe.adapters;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.interfaces.LiveQuizCard;


public class LiveQuizViewHolder extends RecyclerView.ViewHolder {
    ImageView imgViewQuizImage;
    TextView tvTitle, tvSubTitle;
    ImageButton imgButtonEdit, imgButtonDelete;
    public LiveQuizViewHolder(@NonNull View itemView) {
        super(itemView);
        imgViewQuizImage = itemView.findViewById(R.id.recyclerItemLiveQuizImageView);
        tvTitle = itemView.findViewById(R.id.recyclerItemLiveQuizTitleTextView);
        tvSubTitle = itemView.findViewById(R.id.recyclerItemLiveQuizSubTitleTextView);
        imgButtonEdit = itemView.findViewById(R.id.recyclerItemLiveQuizEditImageButton);
        imgButtonDelete = itemView.findViewById(R.id.recyclerItemLiveQuizDeleteImageButton);
    }

    public void bind (LiveQuizCard liveQuizCard){
        imgViewQuizImage.setImageResource(liveQuizCard.getLiveQuizCardImage());
        tvTitle.setText(liveQuizCard.getLiveQuizCardTitle());
        tvSubTitle.setText(liveQuizCard.getLiveQuizCardSubTitle());

    }
}
