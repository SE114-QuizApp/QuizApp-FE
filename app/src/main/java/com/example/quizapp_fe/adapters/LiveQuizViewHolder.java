package com.example.quizapp_fe.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
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
import com.example.quizapp_fe.activities.CreatorQuizActivity;
import com.example.quizapp_fe.activities.QuizDetailActivity;
import com.example.quizapp_fe.activities.UpdateQuizActivity;
import com.example.quizapp_fe.dialogs.DeleteConfirmationDialog;
import com.example.quizapp_fe.interfaces.LiveQuizCard;

import java.util.ArrayList;


public class LiveQuizViewHolder extends RecyclerView.ViewHolder {
    ImageView imgViewQuizImage;
    TextView tvTitle, tvSubTitle;
    ImageButton imgButtonEdit, imgButtonDelete, imgButtonExamine;
    ArrayList<LiveQuizCard> liveQuizList;

    public LiveQuizViewHolder(@NonNull View itemView) {
        super(itemView);
        imgViewQuizImage = itemView.findViewById(R.id.recyclerItemLiveQuizImageView);
        tvTitle = itemView.findViewById(R.id.recyclerItemLiveQuizTitleTextView);
        tvSubTitle = itemView.findViewById(R.id.recyclerItemLiveQuizSubTitleTextView);
        imgButtonEdit = itemView.findViewById(R.id.recyclerItemLiveQuizEditImageButton);
        imgButtonDelete = itemView.findViewById(R.id.recyclerItemLiveQuizDeleteImageButton);
        imgButtonExamine = itemView.findViewById(R.id.recyclerItemLiveQuizExamineImageButton);
    }

    public void bind (LiveQuizCard liveQuizCard, Context context, ArrayList<LiveQuizCard> liveQuizList, DeleteConfirmationDialog.OnDeleteClickListener listener) {
        if (liveQuizCard.getLiveQuizCardImage().isEmpty() || liveQuizCard.getLiveQuizCardImage() == null) {
            Glide.with(context).load("https://res.cloudinary.com/dfoiuc0jw/image/upload/v1702735161/quiz-app/background/DefaultQuizBackGroundLight_hqjyrs.webp")
                 .into(imgViewQuizImage);
        } else {
            Glide.with(context).load(liveQuizCard.getLiveQuizCardImage()).into(imgViewQuizImage);
        }
        tvTitle.setText(liveQuizCard.getLiveQuizCardTitle());
        if (liveQuizCard.getLiveQuizCardSubTitle() == "") {
            tvSubTitle.setVisibility(View.GONE);
        } else {
            tvSubTitle.setText(liveQuizCard.getLiveQuizCardSubTitle());
        }
        this.liveQuizList = liveQuizList;

        imgButtonExamine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.animation_normal);
                imgButtonExamine.startAnimation(animation);
                Intent intent = new Intent(context, QuizDetailActivity.class);
                if (!(context instanceof Activity)) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                intent.putExtra("quizId", liveQuizCard.getLiveQuizCardId());
                context.startActivity(intent);
            }
        });
        imgButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreatorQuizActivity.class);
                if (!(context instanceof Activity)) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                intent.putExtra("quizId", liveQuizCard.getLiveQuizCardId());
                context.startActivity(intent);
            }
        });
        imgButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeleteConfirmationDialogFromAdapterViewHolder(listener);
            }
        });
    }
    private void openDeleteConfirmationDialogFromAdapterViewHolder(DeleteConfirmationDialog.OnDeleteClickListener listener ) {
        DeleteConfirmationDialog.openDeleteConfirmationDialog(Gravity.CENTER, itemView.getContext(), liveQuizList, getBindingAdapterPosition(),listener );
    }
}
