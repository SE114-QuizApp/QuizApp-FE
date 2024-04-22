package com.example.quizapp_fe.fragments;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quizapp_fe.R;
import com.example.quizapp_fe.api.account.profile.GetMeApi;
import com.example.quizapp_fe.dialogs.LoadingDialog;
import com.example.quizapp_fe.models.CredentialToken;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    ImageView imgAvatar;
    TextView tvName;

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;
    LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        loadingDialog = new LoadingDialog(requireContext());
        imgAvatar = view.findViewById(R.id.profile_image);
        tvName = view.findViewById(R.id.tvName);
        ImageView btnBack = view.findViewById(R.id.btnBack);
        ImageView btnSetting = view.findViewById(R.id.btnSetting);

        getMe();

        // initializing variable for bar chart.
        barChart = view.findViewById(R.id.idBarChart);

        // calling method to get bar entries.
        getBarEntries();

        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher();
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.homeMainContainerFrameLayout, new ProfileSettingFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void getBarEntries() {
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList.add(new BarEntry(1f, 4));
        barEntriesArrayList.add(new BarEntry(2f, 6));
        barEntriesArrayList.add(new BarEntry(3f, 8));
        barEntriesArrayList.add(new BarEntry(4f, 2));
        barEntriesArrayList.add(new BarEntry(5f, 4));
        barEntriesArrayList.add(new BarEntry(6f, 1));
    }
    private void getMe() {
        GetMeApi.getAPI(getContext()).getMe().enqueue(new retrofit2.Callback<com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult>() {
            @Override
            public void onResponse(retrofit2.Call<com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult> call, retrofit2.Response<com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;

                    CredentialToken.getInstance(requireContext()).setUserProfile(response.body().getUser());
                    System.out.println(response.body().getUser().getUsername());
                    System.out.printf("%s %s%n", response.body().getUser().getFirstName(), response.body().getUser().getLastName());
                    if(response.body().getUser().getFirstName() != null && response.body().getUser().getLastName() != null) {
                        System.out.println("Full name:");
                        tvName.setText(String.format("%s %s", response.body().getUser().getFirstName(), response.body().getUser().getLastName()));
                    } else {
                        tvName.setText(response.body().getUser().getUsername());
                    }

                    if (response.body().getUser().getAvatar() != null) {
                        Glide.with(requireContext())
                             .load(response.body().getUser().getAvatar())
                             .circleCrop()
                             .into(imgAvatar);
                    } else {
                        imgAvatar.setImageResource(R.drawable.ic_user_24);
                    }

                } else {
                    Toast.makeText(getContext(), "Get Me failure",
                                   Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getContext(), "Call API failure",
                               Toast.LENGTH_SHORT).show();
            }
        });
    }
}