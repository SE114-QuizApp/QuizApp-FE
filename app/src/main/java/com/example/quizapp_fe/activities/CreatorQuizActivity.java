package com.example.quizapp_fe.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.databinding.ActivityCreatorQuizBinding;
import com.example.quizapp_fe.fragments.ChooseCategoryFragment;
import com.example.quizapp_fe.fragments.CreateQuestionFragment;

public class CreatorQuizActivity extends AppCompatActivity {
    ActivityCreatorQuizBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_quiz);

        binding = ActivityCreatorQuizBinding.inflate(getLayoutInflater());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.creatorQuizMainContainerFrameLayout, new ChooseCategoryFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


}
