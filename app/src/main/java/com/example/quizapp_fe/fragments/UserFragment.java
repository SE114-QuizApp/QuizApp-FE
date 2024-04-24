package com.example.quizapp_fe.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.adapters.UserCardAdapter;
import com.example.quizapp_fe.interfaces.UserCard;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<UserCard> userCardArrayList;
    UserCardAdapter userCardAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        recyclerView = view.findViewById(R.id.userFragRecyclerView);
        userCardArrayList = new ArrayList<>();
        userCardArrayList.add(new UserCard(R.drawable.img_lookup, "Phuc Lam", "phuclam@gmail.com"));
        userCardArrayList.add(new UserCard(R.drawable.img_lookup, "Phuc Thinh", "phucthinh@gmail.com"));
        userCardArrayList.add(new UserCard(R.drawable.img_lookup, "Phuc Lam", "phuclam@gmail.com"));
        userCardArrayList.add(new UserCard(R.drawable.img_lookup, "Phuc Lam", "phuclam@gmail.com"));
        userCardArrayList.add(new UserCard(R.drawable.img_lookup, "Phuc Lam", "phuclam@gmail.com"));
        userCardArrayList.add(new UserCard(R.drawable.img_lookup, "Phuc Lam", "phuclam@gmail.com"));
        userCardAdapter = new UserCardAdapter(view.getContext(), userCardArrayList);
        recyclerView.setAdapter(userCardAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        return view;
    }
}