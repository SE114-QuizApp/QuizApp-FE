package com.example.quizapp_fe.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.databinding.ActivityCreatorQuizBinding;
import com.example.quizapp_fe.entities.Category;
import com.example.quizapp_fe.entities.Grade;
import com.example.quizapp_fe.entities.Quiz;
import com.example.quizapp_fe.fragments.ChooseCategoryFragment;
import com.example.quizapp_fe.models.CreateQuizViewModel;

import java.util.ArrayList;

public class CreatorQuizActivity extends AppCompatActivity {
    private ActivityCreatorQuizBinding binding;
    private CreateQuizViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_creator_quiz);
        model = new ViewModelProvider(this).get(CreateQuizViewModel.class);
        binding.setViewModel(model);

        // initialize the quiz object
        if (model.getQuiz().getValue() == null) {
            Quiz quiz = new Quiz();
            quiz.setName("");
            quiz.setDescription("");
            quiz.setBackgroundImage("");
            quiz.setPointsPerQuestion(0);
            quiz.setNumberOfQuestions(0);
            quiz.setPublic(false);
            quiz.setTags(new ArrayList<>());
            quiz.setDraft(true);
            quiz.setQuestionList(new ArrayList<>());
            quiz.setGrade(new Grade("All"));
            quiz.setCategory(new Category(""));
            model.setQuiz(quiz);
        }

        // add the ChooseCategoryFragment to the activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.creatorQuizMainContainerFrameLayout, new ChooseCategoryFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        model.clearQuiz();
    }
}