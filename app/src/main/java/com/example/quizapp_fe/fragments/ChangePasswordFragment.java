package com.example.quizapp_fe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.api.ErrorResponse;
import com.example.quizapp_fe.api.account.profile.ChangePasswordApi;
import com.example.quizapp_fe.dialogs.LoadingDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.logging.Logger;

public class ChangePasswordFragment extends Fragment {
    LoadingDialog loadingDialog;

    EditText edtOldPassword;
    EditText edtNewPassword;
    EditText edtConfirmPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        loadingDialog = new LoadingDialog(requireContext());

        ImageView btnBack = view.findViewById(R.id.btnBack);
        Button btnSave = view.findViewById(R.id.btnSave);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        edtOldPassword = view.findViewById(R.id.edtOldPassword);
        edtNewPassword = view.findViewById(R.id.edtNewPassword);
        edtConfirmPassword = view.findViewById(R.id.edtConfirmPassword);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidData()) {
//                    loadingDialog.show();
                    // Call API to change password
                    String oldPassword = edtOldPassword.getText().toString();
                    String newPassword = edtNewPassword.getText().toString();
                    String confirmPassword = edtConfirmPassword.getText().toString();

                    if (!newPassword.equals(confirmPassword)) {
                        edtConfirmPassword.setError("Password does not match");
                        return;
                    }
                    loadingDialog.showLoading();

                    ChangePasswordApi.getAPI(requireContext()).changePassword(new ChangePasswordApi.API.ChangePasswordRequest(oldPassword, newPassword)).enqueue(new Callback<ChangePasswordApi.API.ChangePasswordResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ChangePasswordApi.API.ChangePasswordResponse> call, @NonNull Response<ChangePasswordApi.API.ChangePasswordResponse> response) {
                            if (response.isSuccessful()) {
                                assert response.body() != null;
                                ChangePasswordApi.API.ChangePasswordResponse result = response.body();

                                Toast.makeText(requireContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
//                                requireActivity().getSupportFragmentManager().popBackStack();
                                loadingDialog.dismiss();
                            } else {
                                Gson gson = new GsonBuilder().create();
                                assert response.errorBody() != null;
                                ErrorResponse error = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                                loadingDialog.showError(error.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<ChangePasswordApi.API.ChangePasswordResponse> call, Throwable t) {
                            Toast.makeText(requireContext(), "Failed to change password", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        return view;
    }


    boolean isValidData() {
        if (edtOldPassword.getText().toString().isEmpty()) {
            edtOldPassword.setError("Old Password is required");
            return false;
        }
        if (edtNewPassword.getText().toString().isEmpty()) {
            edtNewPassword.setError("New Password is required");
            return false;
        }
        if (edtConfirmPassword.getText().toString().isEmpty()) {
            edtConfirmPassword.setError("Confirm Password is required");
            return false;
        }

        if (!edtNewPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
            edtConfirmPassword.setError("Password does not match");
            return false;
        }

        if (edtNewPassword.getText().toString().length() < 8) {
            edtNewPassword.setError("Password must be at least 8 characters");
            return false;
        }

        if (edtNewPassword.getText().toString().equals(edtOldPassword.getText().toString())) {
            edtNewPassword.setError("New password must be different from old password");
            return false;
        }

        return true;
    }
}
