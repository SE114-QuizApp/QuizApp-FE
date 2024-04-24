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
import com.example.quizapp_fe.interfaces.CategoryCard;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<CategoryCard> categoryCardArrayList;
    CategoryCardAdapter categoryCardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        recyclerView = view.findViewById(R.id.categoryFragRecyclerView);
        categoryCardArrayList = new ArrayList<>();
        categoryCardArrayList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardArrayList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardArrayList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardArrayList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardArrayList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardArrayList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardArrayList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardArrayList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardAdapter = new CategoryCardAdapter(view.getContext(), categoryCardArrayList);
        recyclerView.setAdapter(categoryCardAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        return view;
    }
}