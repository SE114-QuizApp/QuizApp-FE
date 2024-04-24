package com.example.quizapp_fe.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.quizapp_fe.fragments.HomeFragment;
import com.example.quizapp_fe.fragments.ProfileFragment;
import com.example.quizapp_fe.R;
import com.example.quizapp_fe.fragments.RankingFragment;
import com.example.quizapp_fe.databinding.ActivityHomeBinding;
import com.example.quizapp_fe.fragments.DiscoveryFragment;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



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
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeMainContainerFrameLayout, fragment);
        fragmentTransaction.commit();
    }
}