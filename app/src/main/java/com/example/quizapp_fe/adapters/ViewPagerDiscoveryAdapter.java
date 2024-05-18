package com.example.quizapp_fe.adapters;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quizapp_fe.fragments.CategoryFragment;
import com.example.quizapp_fe.fragments.QuizFragment;
import com.example.quizapp_fe.fragments.UserFragment;

public class ViewPagerDiscoveryAdapter extends FragmentStateAdapter {


    public ViewPagerDiscoveryAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new UserFragment();
            case 1:
                return new QuizFragment();
            case 2:
                return new CategoryFragment();
            default:
                return new UserFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
