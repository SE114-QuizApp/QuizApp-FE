package com.example.quizapp_fe.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.api.quiz.get.GetQuizByIdApi;
import com.example.quizapp_fe.entities.Category;
import com.example.quizapp_fe.entities.Grade;
import com.example.quizapp_fe.entities.Quiz;
import com.example.quizapp_fe.fragments.ChooseCategoryFragment;
import com.example.quizapp_fe.interfaces.OnBackPressedListener;
import com.example.quizapp_fe.models.CreateQuizViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatorQuizActivity extends AppCompatActivity {

    private CreateQuizViewModel createQuizViewModel;

    private String quizId;

    private Quiz quiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_creator_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.creatorQuizContainer), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        createQuizViewModel = new ViewModelProvider(this).get(CreateQuizViewModel.class);
        createQuizViewModel.setIsUpdate(false);

        Intent intent = getIntent();
        quizId = intent.getStringExtra("quizId");

        if (quizId != null) {
            GetQuizByIdApi.getAPI(CreatorQuizActivity.this).getQuizById(quizId).enqueue(new Callback<Quiz>() {
                @Override
                public void onResponse(@NonNull Call<Quiz> call, @NonNull Response<Quiz> response) {
                    if (response.isSuccessful()) {
                        quiz = response.body();

                        createQuizViewModel.setQuiz(quiz);
                        createQuizViewModel.setIsUpdate(true);

                        fragmentTransaction.add(R.id.creatorQuizContainer, new ChooseCategoryFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Quiz> call, @NonNull Throwable t) {
                    Toast.makeText(CreatorQuizActivity.this, "Call API failure", Toast.LENGTH_SHORT).show();
                }
            });

            return;
        }

        if (createQuizViewModel.getQuiz().getValue() == null) {
            quiz = new Quiz();
            quiz.setName("");
            quiz.setDescription("");
            quiz.setBackgroundImage("");
            quiz.setPointsPerQuestion(0);
            quiz.setNumberOfQuestions(0);
            quiz.setPublic(false);
            quiz.setTags(new ArrayList<>());
            quiz.setDraft(true);
            quiz.setQuestionList(new ArrayList<>());
            quiz.setGrade(new Grade());
            quiz.setCategory(new Category());
            createQuizViewModel.setQuiz(quiz);
        }


        fragmentTransaction.add(R.id.creatorQuizContainer, new ChooseCategoryFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        createQuizViewModel.clearQuiz();
    }
}