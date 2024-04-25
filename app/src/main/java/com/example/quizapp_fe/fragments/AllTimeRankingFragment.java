package com.example.quizapp_fe.fragments;

import static com.example.quizapp_fe.R.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quizapp_fe.R;
import com.example.quizapp_fe.adapters.UserRankRecViewAdapter;
import com.example.quizapp_fe.entities.UserRank;
import com.example.quizapp_fe.models.RankingUsers;

import java.util.ArrayList;

public class AllTimeRankingFragment extends Fragment {
    ImageView imgAvatarUser1, imgAvatarUser2, imgAvatarUser3;
    TextView txtNameUser1, txtNameUser2, txtNameUser3;
    TextView txtScoreUser1, txtScoreUser2, txtScoreUser3;

    RecyclerView userRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking_all_time, container, false);

        setDataTop3(view);
        userRecyclerView = view.findViewById(R.id.userRecyclerView);

        //get users rank from top 4 to end
        ArrayList<UserRank> userListRanking = new ArrayList<>();
        for(UserRank userRank : RankingUsers.getInstance().getUsers()) {
            if(userRank.getRank() > 3) {
                userListRanking.add(userRank);
            }
        }

        UserRankRecViewAdapter userRecViewAdapter = new UserRankRecViewAdapter(requireContext());
        userRecViewAdapter.setUserList(userListRanking);
        userRecyclerView.setAdapter(userRecViewAdapter);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        return view;
    }

    void setDataTop3(View view) {
        // Set data for top 3 users
        txtNameUser1 = view.findViewById(R.id.name1);
        txtNameUser2 = view.findViewById(R.id.name2);
        txtNameUser3 = view.findViewById(R.id.name3);

        txtScoreUser1 = view.findViewById(R.id.point1);
        txtScoreUser2 = view.findViewById(R.id.point2);
        txtScoreUser3 = view.findViewById(R.id.point3);

        imgAvatarUser1 = view.findViewById(R.id.avatar1);
        imgAvatarUser2 = view.findViewById(R.id.avatar2);
        imgAvatarUser3 = view.findViewById(R.id.avatar3);

        for(UserRank userRank : RankingUsers.getInstance().getUsers()) {
            if(userRank.getRank() == 1) {
                txtNameUser1.setText(userRank.getUsername());
                txtScoreUser1.setText(String.valueOf(userRank.getPoint()));
                Glide.with(requireContext())
                        .asBitmap()
                        .load(userRank.getAvatar())
                        .error(R.drawable.default_avatar)
                        .into(imgAvatarUser1);
            } else if(userRank.getRank() == 2) {
                txtNameUser2.setText(userRank.getUsername());
                txtScoreUser2.setText(String.valueOf(userRank.getPoint()));
                Glide.with(requireContext())
                        .asBitmap()
                        .load(userRank.getAvatar())
                        .error(R.drawable.default_avatar)
                        .into(imgAvatarUser2);
            } else if(userRank.getRank() == 3) {
                txtNameUser3.setText(userRank.getUsername());
                txtScoreUser3.setText(String.valueOf(userRank.getPoint()));
                Glide.with(requireContext())
                        .asBitmap()
                        .load(userRank.getAvatar())
                        .error(R.drawable.default_avatar)
                        .into(imgAvatarUser3);
            }
        }
    }
}
