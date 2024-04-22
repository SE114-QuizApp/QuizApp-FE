package com.example.quizapp_fe.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.adapters.CategoryCardAdapter;
import com.example.quizapp_fe.interfaces.CategoryCard;

import java.util.ArrayList;
import java.util.List;

public class DiscoveryFragment extends Fragment {

    RecyclerView categoryRecyclerView;
    List<CategoryCard> categoryCardList;
    CategoryCardAdapter categoryCardAdapter;
    CategoryCard categoryCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);
        categoryRecyclerView = view.findViewById(R.id.discoveryFragCategoryRecyclerView);
        categoryCardList = new ArrayList<>();
        categoryCardList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardAdapter = new CategoryCardAdapter(view.getContext(), categoryCardList);
        categoryRecyclerView.setAdapter(categoryCardAdapter);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        return view;
    }
}