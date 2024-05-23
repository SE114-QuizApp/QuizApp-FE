package com.example.quizapp_fe.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.QuizDetailActivity;
import com.example.quizapp_fe.activities.UpdateQuizActivity;
import com.example.quizapp_fe.api.quiz.delete.DeleteQuizByIdApi;
import com.example.quizapp_fe.entities.Quiz;
import com.example.quizapp_fe.interfaces.LiveQuizCard;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LiveQuizViewHolder extends RecyclerView.ViewHolder {
    public interface OnDeleteClickListener {
        void onDelete(int position);
    }
    ImageView imgViewQuizImage;
    TextView tvTitle, tvSubTitle;
    ImageButton imgButtonEdit, imgButtonDelete, imgButtonExamine;
    ArrayList<LiveQuizCard> liveQuizList;
    OnDeleteClickListener deleteClickListener;
    public LiveQuizViewHolder(@NonNull View itemView, OnDeleteClickListener onDeleteClickListener) {
        super(itemView);
        imgViewQuizImage = itemView.findViewById(R.id.recyclerItemLiveQuizImageView);
        tvTitle = itemView.findViewById(R.id.recyclerItemLiveQuizTitleTextView);
        tvSubTitle = itemView.findViewById(R.id.recyclerItemLiveQuizSubTitleTextView);
        imgButtonEdit = itemView.findViewById(R.id.recyclerItemLiveQuizEditImageButton);
        imgButtonDelete = itemView.findViewById(R.id.recyclerItemLiveQuizDeleteImageButton);
        imgButtonExamine = itemView.findViewById(R.id.recyclerItemLiveQuizExamineImageButton);
        this.deleteClickListener = onDeleteClickListener;

    }

    public void bind (LiveQuizCard liveQuizCard, Context context, ArrayList<LiveQuizCard> liveQuizList){
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
        this.liveQuizList =liveQuizList;

        imgButtonExamine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(context,R.anim.animation_normal);
                imgButtonExamine.startAnimation(animation);
                Intent intent = new Intent(context, QuizDetailActivity.class);
                if (!(context instanceof Activity)) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);}
                intent.putExtra("quizId", liveQuizCard.getLiveQuizCardId());
                context.startActivity(intent);
            }
        });
        imgButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , UpdateQuizActivity.class);
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
                openDeleteConfirmationDialog(Gravity.CENTER, context);
            }
        });
    }
    public void openDeleteConfirmationDialog(int gravity, Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_confirmation);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = gravity;
        window.setAttributes(windowAttribute);
        Button cancleConfirmButton, deleteConfirmButton;
        cancleConfirmButton = dialog.findViewById(R.id.deleteConfirmationCancelButton);
        deleteConfirmButton = dialog.findViewById(R.id.deleteConfirmationDeleteButton);
        cancleConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        deleteConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positionInList = getBindingAdapterPosition();
                if (positionInList != RecyclerView.NO_POSITION) {
                    String quizId = liveQuizList.get(positionInList).getLiveQuizCardId();
                    Log.e("Check Quiz Id Before Delete", quizId);
                    liveQuizList.remove(positionInList);
                    Call<Quiz> callDeleteQuiz = DeleteQuizByIdApi.getAPI(context).DeleteQuizAPI(quizId);

                    callDeleteQuiz.enqueue(new Callback<Quiz>() {
                        @Override
                        public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context, "Delete quiz Successful", Toast.LENGTH_SHORT).show();
                                Log.e("check delete quiz", "Delete quiz successful");
                            }
                        }

                        @Override
                        public void onFailure(Call<Quiz> call, Throwable t) {
                            Toast.makeText(context, "Delete quiz Failed", Toast.LENGTH_SHORT).show();
                            Log.e("check delete quiz", "Delete quiz Failed");
                        }
                    });
                    deleteClickListener.onDelete(positionInList);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}
