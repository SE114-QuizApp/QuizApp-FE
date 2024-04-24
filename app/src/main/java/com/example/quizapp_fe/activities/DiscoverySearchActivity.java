package com.example.quizapp_fe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.ViewPagerDiscoveryAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DiscoverySearchActivity extends AppCompatActivity {

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
    }
}