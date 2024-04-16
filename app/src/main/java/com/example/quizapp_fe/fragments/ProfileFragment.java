package com.example.quizapp_fe.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.OnboardingActivity;
import com.example.quizapp_fe.dialogs.LoadingDialog;
import com.example.quizapp_fe.models.CredentialToken;

import java.util.Objects;


public class ProfileFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button btnLogOut = view.findViewById(R.id.btnLogout);

        LoadingDialog loadingDialog = new LoadingDialog(requireContext());

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