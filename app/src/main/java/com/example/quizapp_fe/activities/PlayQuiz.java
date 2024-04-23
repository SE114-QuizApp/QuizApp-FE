package com.example.quizapp_fe.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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

public class PlayQuiz extends AppCompatActivity {

    ImageView questionImage;

    LinearLayout titleAndImageQuestion;
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

        questionImage = findViewById(R.id.questionImage);
        titleAndImageQuestion = findViewById(R.id.titleAndImageQuestion);
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

    }
}