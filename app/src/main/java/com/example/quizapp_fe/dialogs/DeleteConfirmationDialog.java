package com.example.quizapp_fe.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.api.quiz.delete.DeleteQuizByIdApi;
import com.example.quizapp_fe.entities.Quiz;
import com.example.quizapp_fe.interfaces.LiveQuizCard;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteConfirmationDialog {
    public interface OnDeleteClickListener {
        void onDelete(int position);
    }

    public static void openDeleteConfirmationDialog(int gravity, Context context, ArrayList<LiveQuizCard> liveQuizList, int positionInList, OnDeleteClickListener deleteClickListener) {
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
                if (positionInList != RecyclerView.NO_POSITION) {
                    String quizId = liveQuizList.get(positionInList).getLiveQuizCardId();
                    Log.e("Check Quiz Id Before Delete", quizId);
                    liveQuizList.remove(positionInList);
                    Call<Quiz> callDeleteQuiz = DeleteQuizByIdApi.getAPI(context).DeleteQuizAPI(quizId);
                    callDeleteQuiz.enqueue(new Callback<Quiz>() {
                        @Override
                        public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                            if (response.isSuccessful()){
                                Log.e("check Delete Quiz ", "successful");
                            }
                        }

                        @Override
                        public void onFailure(Call<Quiz> call, Throwable t) {
                            Log.e("check Delete Quiz ", "failed");
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
