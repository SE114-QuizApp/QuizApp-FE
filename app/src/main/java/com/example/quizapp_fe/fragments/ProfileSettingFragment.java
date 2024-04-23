package com.example.quizapp_fe.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.OnboardingActivity;
import com.example.quizapp_fe.databinding.ActivityHomeBinding;
import com.example.quizapp_fe.dialogs.LoadingDialog;
import com.example.quizapp_fe.models.CredentialToken;

import java.util.HashMap;

public class ProfileSettingFragment extends Fragment {

    LoadingDialog loadingDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_settings, container, false);
        loadingDialog = new LoadingDialog(requireContext());

        ImageView btnBack = view.findViewById(R.id.btnBack);
        Button btnLogOut = view.findViewById(R.id.btnLogOut);


        LinearLayout btnEditProfile = view.findViewById(R.id.btnEditProfile);
        LinearLayout btnChangePassword = view.findViewById(R.id.btnEditPassword);
        LinearLayout btnChangeEmail = view.findViewById(R.id.btnEditEmail);


        LoadingDialog loadingDialog = new LoadingDialog(requireContext());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
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

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.homeMainContainerFrameLayout, new EditProfileFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.homeMainContainerFrameLayout, new ChangePasswordFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }



}
