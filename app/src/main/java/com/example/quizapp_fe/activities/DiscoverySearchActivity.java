package com.example.quizapp_fe.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.adapters.ViewPagerDiscoveryAdapter;
import com.example.quizapp_fe.services.SearchListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DiscoverySearchActivity extends AppCompatActivity implements SearchListener {

    ImageView backArrowImageView;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerDiscoveryAdapter viewPagerDiscoveryAdapter;
    private SearchView discoverySearchSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery_search);
        discoverySearchSearchView = findViewById(R.id.discoverySearchSearchView);
        tabLayout = findViewById(R.id.discoverySearchTabLayout);
        viewPager2 = findViewById(R.id.discoverySearchViewPager);
        viewPagerDiscoveryAdapter = new ViewPagerDiscoveryAdapter(this);
        viewPager2.setAdapter(viewPagerDiscoveryAdapter);
        viewPager2.setCurrentItem(1);
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

        OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();
        dispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(DiscoverySearchActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        discoverySearchSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                performSearch(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                performSearch(s);
                return true;
            }
        });

        backArrowImageView = findViewById(R.id.discoverySearchBackArrowImageView);
        backArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiscoverySearchActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSearchQuery(String query) {
        Fragment currentFragment = viewPagerDiscoveryAdapter.getFragment(viewPager2.getCurrentItem());
        if (currentFragment instanceof SearchListener) {
            ((SearchListener) currentFragment).onSearchQuery(query);
        }
    }

    public void performSearch(String query) {
        onSearchQuery(query);
    }
}