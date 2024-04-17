package com.example.quizapp_fe.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.OnboardingActivity;
import com.example.quizapp_fe.activities.SignInActivity;
import com.example.quizapp_fe.api.account.profile.GetMeApi;
import com.example.quizapp_fe.dialogs.LoadingDialog;
import com.example.quizapp_fe.models.CredentialToken;

import java.util.Objects;


public class ProfileFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button btnLogOut = view.findViewById(R.id.btnLogout);
        Button btnGetMe = view.findViewById(R.id.btnGetMe);
        ImageView imgProfile = view.findViewById(R.id.imgAvatar);

        LoadingDialog loadingDialog = new LoadingDialog(requireContext());

        btnGetMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetMeApi.getAPI(getContext()).getMe().enqueue(new retrofit2.Callback<com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult>() {
                    @Override
                    public void onResponse(retrofit2.Call<com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult> call, retrofit2.Response<com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            System.out.println(response.body().getUser().toString());
                            Toast.makeText(getContext(), "Get Me success",
                                           Toast.LENGTH_SHORT).show();
                            if(response.body().getUser().getAvatar() != null){
                                Glide.with(requireContext())
                                        .asBitmap()
                                        .load(response.body().getUser().getAvatar())
                                        .into(imgProfile);
                            }

                        } else {
                            System.out.println(response.errorBody().toString());
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
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.showLoading("Logging out...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CredentialToken.getInstance(getContext()).clearCredential();
                        Intent intent = new Intent(getContext(), OnboardingActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                        loadingDialog.dismiss();
                    }
                }, 1500);

            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}