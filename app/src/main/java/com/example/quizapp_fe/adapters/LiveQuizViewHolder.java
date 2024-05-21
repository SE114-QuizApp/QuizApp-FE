package com.example.quizapp_fe.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.QuizDetailActivity;
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

    public void bind (LiveQuizCard liveQuizCard, Context context){
        Glide.with(context).asBitmap().load(liveQuizCard.getLiveQuizCardImage()).into(imgViewQuizImage);
        tvTitle.setText(liveQuizCard.getLiveQuizCardTitle());
        tvSubTitle.setText(liveQuizCard.getLiveQuizCardSubTitle());
        imgButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.animation_normal);
                imgButtonEdit.startAnimation(animation);
                Intent intent = new Intent(context, QuizDetailActivity.class);
                intent.putExtra("quizId", liveQuizCard.getLiveQuizCardId());
                context.startActivity(intent);
            }
        });

    }
}
