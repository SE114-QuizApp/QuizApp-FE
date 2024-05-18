package com.example.quizapp_fe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.adapters.LiveQuizAdapter;
import com.example.quizapp_fe.api.quiz.get.GetPublicQuizzesApi;
import com.example.quizapp_fe.entities.Quiz;
import com.example.quizapp_fe.fragments.HomeFragment;
import com.example.quizapp_fe.interfaces.LiveQuizCard;
import com.example.quizapp_fe.models.CredentialToken;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherQuizActivity extends AppCompatActivity {
    RecyclerView listQuizRecyclerView;
    ArrayList<LiveQuizCard> quizCardList;
    LiveQuizAdapter quizCardAdapter;
    LiveQuizCard quizCard;

    ImageButton imgButtonBackArrow;
    String teacherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_quiz);

        listQuizRecyclerView = findViewById(R.id.teacherQuizListQuizRecyclerView);
        imgButtonBackArrow = findViewById(R.id.teacherQuizBackArrowImageButton);

        Intent intent = getIntent();

        teacherId = CredentialToken.getInstance(TeacherQuizActivity.this).getUserProfile().getId();
        quizCardList = new ArrayList<>();
        callApiGetQuiz(teacherId);
//        quizCardAdapter = new LiveQuizAdapter(this.getBaseContext(),quizCardList);
//        listQuizRecyclerView.setAdapter(quizCardAdapter);
//        listQuizRecyclerView.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));

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
    private void callApiGetQuiz(String teacherId){
        GetPublicQuizzesApi.getTeacherQuizAPI(TeacherQuizActivity.this).getTeacherQuiz(teacherId).enqueue(new Callback<ArrayList<Quiz>>() {
            @Override
            public void onResponse(Call<ArrayList<Quiz>> call, Response<ArrayList<Quiz>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Quiz> teacherQuizList = response.body();
                    assert teacherQuizList != null;
                    for (int i = 0; i < teacherQuizList.size(); i++) {
                        String quizTitle;
                        String quizSubTitle;
                        String quizImage;
                        quizTitle = teacherQuizList.get(i).getName();
                        quizSubTitle = teacherQuizList.get(i).getDescription();
                        quizImage = teacherQuizList.get(i).getBackgroundImage();
                        LiveQuizCard liveQuizCard = new LiveQuizCard(quizImage, quizTitle, quizSubTitle);
                        quizCardList.add(liveQuizCard);
                        quizCardAdapter = new LiveQuizAdapter(getBaseContext(), quizCardList);
                        listQuizRecyclerView.setAdapter(quizCardAdapter);
                        listQuizRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Quiz>> call, Throwable t) {
                Toast.makeText(TeacherQuizActivity.this, "Call Quiz In Teacher Quiz Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}