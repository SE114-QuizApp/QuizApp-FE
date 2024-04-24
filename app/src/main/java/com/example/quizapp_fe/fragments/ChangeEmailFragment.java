package com.example.quizapp_fe.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.dialogs.LoadingDialog;

public class ChangeEmailFragment extends Fragment {
    LoadingDialog loadingDialog;
    EditText edtNewEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_email, container, false);

        loadingDialog = new LoadingDialog(requireContext());

        ImageView btnBack = view.findViewById(R.id.btnBack);
        Button btnSave = view.findViewById(R.id.btnSave);

        edtNewEmail = view.findViewById(R.id.edtNewEmail);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidEmail()) {
                    return;
                }
                loadingDialog.showLoading();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismiss();
                        edtNewEmail.setText("");
                        Toast.makeText(requireContext(), "Email changed successfully", Toast.LENGTH_SHORT).show();
                    }
                }, 1500);
            }
        });
        return view;
    }

    boolean isValidEmail() {
        String email = edtNewEmail.getText().toString().trim();
        if (email.isEmpty()) {
            edtNewEmail.setError("Email is required");
            return false;
        }
        //regex for email validation
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern)) {
            edtNewEmail.setError("Invalid email address");
            return false;
        }
        return true;
    }
}
