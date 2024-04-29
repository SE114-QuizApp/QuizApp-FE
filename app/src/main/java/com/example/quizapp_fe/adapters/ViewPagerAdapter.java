package com.example.quizapp_fe.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private final Fragment[] fragments;
    private final String[] titles;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Fragment[] fragments, String[] titles) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
        this.titles = titles;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments[position];
    }

    @Override
    public int getItemCount() {
        return fragments.length;
    }

    @Override
    public long getItemId(int position) {
        return fragments[position].hashCode();
    }

    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
