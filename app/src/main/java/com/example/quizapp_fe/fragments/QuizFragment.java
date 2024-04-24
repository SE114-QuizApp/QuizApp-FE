package com.example.quizapp_fe.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.adapters.CategoryCardAdapter;
import com.example.quizapp_fe.adapters.QuizCardAdapter;
import com.example.quizapp_fe.interfaces.CategoryCard;
import com.example.quizapp_fe.interfaces.QuizCard;

import java.util.ArrayList;

public class QuizFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<QuizCard> quizCardArrayList;
    QuizCardAdapter quizCardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        recyclerView = view.findViewById(R.id.quizFragRecyclerView);
        quizCardArrayList = new ArrayList<>();
        quizCardArrayList.add(new QuizCard(R.drawable.img_sample_quiz, "Nhap mon lap trinh", "Phuc Lam", "UpdateCreated: 2 days"));
        quizCardArrayList.add(new QuizCard(R.drawable.img_sample_quiz, "Cau truc du lieu va giai thuat", "Phuc Thinh", "UpdateCreated: 2 days"));
        quizCardArrayList.add(new QuizCard(R.drawable.img_sample_quiz, "Nhap mon lap trinh", "Phuc Lam", "UpdateCreated: 2 days"));
        quizCardArrayList.add(new QuizCard(R.drawable.img_sample_quiz, "Nhap mon lap trinh", "Phuc Lam", "UpdateCreated: 2 days"));
        quizCardArrayList.add(new QuizCard(R.drawable.img_sample_quiz, "Nhap mon lap trinh", "Phuc Lam", "UpdateCreated: 2 days"));
        quizCardArrayList.add(new QuizCard(R.drawable.img_sample_quiz, "Nhap mon lap trinh", "Phuc Lam", "UpdateCreated: 2 days"));
        quizCardAdapter = new QuizCardAdapter(view.getContext(), quizCardArrayList);
        recyclerView.setAdapter(quizCardAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        return view;
    }
}