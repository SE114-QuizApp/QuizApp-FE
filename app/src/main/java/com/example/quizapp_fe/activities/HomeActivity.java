package com.example.quizapp_fe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quizapp_fe.R;

import android.util.Log;
import android.widget.Toast;

import com.example.quizapp_fe.adapters.UserRankRecViewAdapter;

import com.example.quizapp_fe.api.quiz.get.GetDiscoverQuizzesApi;
import com.example.quizapp_fe.api.user.getListRanking.GetListRankingApi;
import com.example.quizapp_fe.databinding.ActivityHomeBinding;
import com.example.quizapp_fe.entities.UserProfile;
import com.example.quizapp_fe.entities.UserRank;
import com.example.quizapp_fe.fragments.DiscoveryFragment;
import com.example.quizapp_fe.fragments.HomeFragment;
import com.example.quizapp_fe.fragments.ProfileFragment;
import com.example.quizapp_fe.fragments.RankingFragment;
import com.example.quizapp_fe.models.CredentialToken;
import com.example.quizapp_fe.models.DiscoverQuizzes;
import com.example.quizapp_fe.models.RankingUsers;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getRankingData();


        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.bottomMenuHome) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.bottomMenuDiscovery) {
                replaceFragment(new DiscoveryFragment());
            } else if (itemId == R.id.bottomMenuRanking) {
                replaceFragment(new RankingFragment());
            } else if (itemId == R.id.bottomMenuProfile) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });

        binding.btnCreatorQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CreatorQuizActivity.class);
            startActivity(intent);
            finish();
        });

//        getDiscoveryData();

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeMainContainerFrameLayout, fragment);
        fragmentTransaction.commit();
    }

    void getRankingData(){
        GetListRankingApi.getAPI(HomeActivity.this).getListRanking().enqueue(new Callback<ArrayList<UserRank>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<UserRank>> call, @NonNull Response<ArrayList<UserRank>> response) {

                if (response.isSuccessful()){
                    ArrayList<UserRank> userList = response.body();
                    assert userList != null;
                    RankingUsers.getInstance().setUsers(userList);
                    UserProfile currentUser = CredentialToken.getInstance(HomeActivity.this).getUserProfile();
                    //find in userList the current user and set rank for it
                    UserRank currentUserRank = null;
                    for (UserRank user : userList) {
                        if (user.getId().equals(currentUser.getId())){
                            currentUserRank = new UserRank(currentUser, user.getRank());
                            RankingUsers.getInstance().setCurrentUser(currentUserRank);
                            break;
                        }
                    }

                }else {
                    Toast.makeText(HomeActivity.this, "Get ranking data failure", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserRank>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Call api failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getDiscoveryData() {
        GetDiscoverQuizzesApi.getAPI(HomeActivity.this).getDiscoverQuizzes().enqueue(new Callback<DiscoverQuizzes>() {
            @Override
            public void onResponse(Call<DiscoverQuizzes> call, Response<DiscoverQuizzes> response) {
                Toast.makeText(HomeActivity.this, "Call Discover API Successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DiscoverQuizzes> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Call Discover API Fail", Toast.LENGTH_SHORT).show();
                Log.e("DISCOVER", "Call Discover API Fail");
            }
        });
    }
}