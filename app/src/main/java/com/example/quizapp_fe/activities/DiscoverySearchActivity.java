package com.example.quizapp_fe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.adapters.ViewPagerDiscoveryAdapter;
import com.example.quizapp_fe.fragments.DiscoveryFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DiscoverySearchActivity extends AppCompatActivity {

    ImageView backArrowImageView;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerDiscoveryAdapter viewPagerDiscoveryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery_search);
        tabLayout = findViewById(R.id.discoverySearchTabLayout);
        viewPager2 = findViewById(R.id.discoverySearchViewPager);
        viewPagerDiscoveryAdapter = new ViewPagerDiscoveryAdapter(this);
        viewPager2.setAdapter(viewPagerDiscoveryAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Users");
                        break;
                    case 1:
                        tab.setText("Quizzes");
                        break;
                    case 2:
                        tab.setText("Categories");
                        break;
                }
            }
        }).attach();

        backArrowImageView = findViewById(R.id.discoverySearchBackArrowImageView);
        backArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_normal);
                backArrowImageView.startAnimation(animation);
                Intent intent = new Intent(DiscoverySearchActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}