package com.example.quizapp_fe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.adapters.LiveQuizAdapter;
import com.example.quizapp_fe.fragments.HomeFragment;
import com.example.quizapp_fe.interfaces.LiveQuizCard;

import java.util.ArrayList;

public class TeacherQuizActivity extends AppCompatActivity {
    RecyclerView listQuizRecyclerView;
    ArrayList<LiveQuizCard> quizCardList;
    LiveQuizAdapter quizCardAdapter;
    LiveQuizCard quizCard;

    ImageButton imgButtonBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_quiz);

        listQuizRecyclerView = findViewById(R.id.teacherQuizListQuizRecyclerView);
        imgButtonBackArrow = findViewById(R.id.teacherQuizBackArrowImageButton);

        quizCardList = new ArrayList<>();
        quizCardList.add(new LiveQuizCard(R.drawable.img_sample_quiz, "Statistic Math Quiz", "Math - 12 Quizzes"));
        quizCardList.add(new LiveQuizCard(R.drawable.img_sample_quiz, "Statistic Math Quiz", "Math - 12 Quizzes"));
        quizCardList.add(new LiveQuizCard(R.drawable.img_sample_quiz, "Statistic Math Quiz", "Math - 12 Quizzes"));
        quizCardList.add(new LiveQuizCard(R.drawable.img_sample_quiz, "Statistic Math Quiz", "Math - 12 Quizzes"));
        quizCardList.add(new LiveQuizCard(R.drawable.img_sample_quiz, "Statistic Math Quiz", "Math - 12 Quizzes"));
        quizCardList.add(new LiveQuizCard(R.drawable.img_sample_quiz, "Statistic Math Quiz", "Math - 12 Quizzes"));
        quizCardList.add(new LiveQuizCard(R.drawable.img_sample_quiz, "Statistic Math Quiz", "Math - 12 Quizzes"));
        quizCardAdapter = new LiveQuizAdapter(this.getBaseContext(),quizCardList);
        listQuizRecyclerView.setAdapter(quizCardAdapter);
        listQuizRecyclerView.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));

        imgButtonBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(v.getContext(), R.anim.animation_fade_in);
                imgButtonBackArrow.startAnimation(animation);
                Intent intent = new Intent(v.getContext(), HomeFragment.class);
                startActivity(intent);
            }
        });

    }
}