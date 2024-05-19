package com.example.quizapp_fe.adapters;


import android.util.SparseArray;

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
    private SparseArray<Fragment> registeredFragments = new SparseArray<>();
    public ViewPagerDiscoveryAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new UserFragment();
                break;
            case 1:
                fragment = new QuizFragment();
                break;
            case 2:
                fragment = new CategoryFragment();
                break;
            default:
                fragment = new QuizFragment();
        }
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public Fragment getFragment(int position) {
        return registeredFragments.get(position);
    }
}
