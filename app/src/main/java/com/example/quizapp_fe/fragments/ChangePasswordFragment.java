package com.example.quizapp_fe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.dialogs.LoadingDialog;

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
                if (isValidData()){
//                    loadingDialog.show();
                    // Call API to change password
                    String oldPassword = edtOldPassword.getText().toString();
                    String newPassword = edtNewPassword.getText().toString();
                    String confirmPassword = edtConfirmPassword.getText().toString();

                    System.out.println("Old Password: " + oldPassword);
                    System.out.println("New Password: " + newPassword);
                    System.out.println("Confirm Password: " + confirmPassword);
                }
            }
        });

        return view;
    }


    boolean isValidData(){
        if (edtOldPassword.getText().toString().isEmpty()){
            edtOldPassword.setError("Old Password is required");
            return false;
        }
        if (edtNewPassword.getText().toString().isEmpty()){
            edtNewPassword.setError("New Password is required");
            return false;
        }
        if (edtConfirmPassword.getText().toString().isEmpty()){
            edtConfirmPassword.setError("Confirm Password is required");
            return false;
        }

        if (!edtNewPassword.getText().toString().equals(edtConfirmPassword.getText().toString())){
            edtConfirmPassword.setError("Password does not match");
            return false;
        }

        if(edtNewPassword.getText().toString().length() < 8){
            edtNewPassword.setError("Password must be at least 8 characters");
            return false;
        }

        if(edtNewPassword.getText().toString().equals(edtOldPassword.getText().toString())){
            edtNewPassword.setError("New password must be different from old password");
            return false;
        }

        return true;
    }
}
