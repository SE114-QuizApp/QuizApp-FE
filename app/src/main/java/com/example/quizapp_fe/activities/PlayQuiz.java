package com.example.quizapp_fe.activities;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quizapp_fe.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class PlayQuiz extends AppCompatActivity {
    private TextView countDownText;

    private CircularProgressIndicator timeRemainProgress;

    private LinearLayout titleAndImageQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.playQuizLinearLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        titleAndImageQuestion = findViewById(R.id.titleAndImageQuestion);
        countDownText = findViewById(R.id.countDownTimeQuestion);
        timeRemainProgress = findViewById(R.id.progressTimeRemaining);

        boolean hasImage = true;
        if(hasImage == false) {
            ViewGroup parent = (ViewGroup) titleAndImageQuestion.getParent();
            int index = parent.indexOfChild(titleAndImageQuestion);
            // Nếu không có Image, ẩn LinearLayout
            titleAndImageQuestion.setVisibility(View.GONE);

            // Tạo TextView mới
            TextView textView = new TextView(this);
            textView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText("Which three players shared the Premier League Golden Boot in 2018-19 ?");
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(20);
            parent.addView(textView, index);
        }
        // Bắt đầu đếm ngược từ 10 giây
        startCountdown(10);
    }

    public void startCountdown(long seconds) {
        timeRemainProgress.setProgress(100);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(timeRemainProgress, "progress", 0);
        progressAnimator.setDuration(seconds * 1000);
        progressAnimator.start();
        new CountDownTimer(seconds * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                // Cập nhật giá trị đếm ngược trên TextView
                countDownText.setText(millisUntilFinished / 1000 + "");
            }

            public void onFinish() {
                // Đếm ngược hoàn thành
                countDownText.setText("0");
            }

        }.start();
    }
}