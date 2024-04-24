package com.example.quizapp_fe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.adapters.UserRankRecViewAdapter;
import com.example.quizapp_fe.api.user.getListRanking.GetListRankingApi;
import com.example.quizapp_fe.entities.UserProfile;
import com.example.quizapp_fe.entities.UserRank;
import com.example.quizapp_fe.models.CredentialToken;
import com.example.quizapp_fe.models.RankingUsers;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonthRankingFragment extends Fragment {

    ArrayList<UserRank> userListRanking = new ArrayList<>();
    RecyclerView userRecyclerView ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking_month, container, false);

        userRecyclerView = view.findViewById(R.id.userRecyclerView);

        getRankingData();

        return view;
    }
    void getRankingData(){
        GetListRankingApi.getAPI(requireContext()).getListRanking().enqueue(new Callback<ArrayList<UserRank>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<UserRank>> call, @NonNull Response<ArrayList<UserRank>> response) {

                if (response.isSuccessful()){
                    ArrayList<UserRank> userList = response.body();
                    assert userList != null;
                    RankingUsers.getInstance().setUsers(userList);
                    UserProfile currentUser = CredentialToken.getInstance(requireContext()).getUserProfile();
                    //find in userList the current user and set rank for it
                    UserRank currentUserRank = null;
                    for (UserRank user : userList) {
                        if (user.getId().equals(currentUser.getId())){
                            currentUserRank = new UserRank(currentUser, user.getRank());
                            RankingUsers.getInstance().setCurrentUser(currentUserRank);
                            break;
                        }
                    }


                    userListRanking = userList;

                    UserRankRecViewAdapter userRecViewAdapter = new UserRankRecViewAdapter(requireContext());
                    userRecViewAdapter.setUserList(userListRanking);
                    userRecyclerView.setAdapter(userRecViewAdapter);
                    userRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                }else {
                    Toast.makeText(requireContext(), "Get ranking data failure", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserRank>> call, Throwable t) {
                Toast.makeText(requireContext(), "Call api failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
