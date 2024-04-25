package com.example.quizapp_fe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.TeacherQuizActivity;
import com.example.quizapp_fe.adapters.LiveQuizAdapter;
import com.example.quizapp_fe.interfaces.LiveQuizCard;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView liveQuizRecyclerView;
    ArrayList<LiveQuizCard> liveQuizCardList;
    LiveQuizAdapter liveQuizAdapter;

    TextView seeAllButtonTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        liveQuizRecyclerView = view.findViewById(R.id.homeFragLiveQuizzesRecylerView);
        seeAllButtonTextView = view.findViewById(R.id.homeFragSeeAllTextView);
        liveQuizCardList = new ArrayList<>();
        liveQuizCardList.add(new LiveQuizCard(R.drawable.img_sample_quiz, "Statistic Math Quiz", "Math - 12 Quizzes"));
        liveQuizCardList.add(new LiveQuizCard(R.drawable.img_sample_quiz, "Statistic Math Quiz", "Math - 12 Quizzes"));
        liveQuizCardList.add(new LiveQuizCard(R.drawable.img_sample_quiz, "Statistic Math Quiz", "Math - 12 Quizzes"));
        liveQuizCardList.add(new LiveQuizCard(R.drawable.img_sample_quiz, "Statistic Math Quiz", "Math - 12 Quizzes"));
        liveQuizCardList.add(new LiveQuizCard(R.drawable.img_sample_quiz, "Statistic Math Quiz", "Math - 12 Quizzes"));
        liveQuizCardList.add(new LiveQuizCard(R.drawable.img_sample_quiz, "Statistic Math Quiz", "Math - 12 Quizzes"));
        liveQuizAdapter = new LiveQuizAdapter(view.getContext(),liveQuizCardList);
        liveQuizRecyclerView.setAdapter(liveQuizAdapter);
        liveQuizRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        seeAllButtonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(v.getContext(), R.anim.animation_fade_in);
                seeAllButtonTextView.startAnimation(animation);
                Intent intent = new Intent(v.getContext(), TeacherQuizActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}