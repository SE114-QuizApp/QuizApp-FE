package com.example.quizapp_fe.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.adapters.ViewPagerAdapter;
import com.example.quizapp_fe.api.user.getListRanking.GetListRankingApi;
import com.example.quizapp_fe.dialogs.LoadingDialog;
import com.example.quizapp_fe.entities.UserProfile;
import com.example.quizapp_fe.entities.UserRank;
import com.example.quizapp_fe.models.CredentialToken;
import com.example.quizapp_fe.models.RankingUsers;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingFragment extends Fragment {

    LoadingDialog loadingDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ranking, container, false);
        loadingDialog = new LoadingDialog(requireContext());

        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        Fragment [] fragments = new Fragment[]{new MonthRankingFragment(), new AllTimeRankingFragment()};
        String[] titles = new String[]{"Month", "All Time"};

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), getLifecycle(), fragments, titles);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(titles[position])).attach();

        return view;
    }

    public void getRankingData(){
        GetListRankingApi.getAPI(requireContext()).getListRanking().enqueue(new Callback<ArrayList<UserRank>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<UserRank>> call, @NonNull Response<ArrayList<UserRank>> response) {

                if (response.isSuccessful()){
                    ArrayList<UserRank> userList = response.body();
                    assert userList != null;
                    RankingUsers.getInstance().setUsers(userList);
                    UserProfile currentUser = CredentialToken.getInstance(requireContext()).getUserProfile();
                    Logger.getLogger("MonthRankingFragment").info("UserList: " + userList);
                    //find in userList the current user and set rank for it
                    UserRank currentUserRank;
                    for (UserRank user : userList) {
                        if (user.getId().equals(currentUser.getId())){
                            currentUserRank = new UserRank(currentUser, user.getRank());
                            break;
                        }
                    }
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