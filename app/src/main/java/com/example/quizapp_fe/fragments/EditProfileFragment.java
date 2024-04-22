package com.example.quizapp_fe.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.quizapp_fe.R;
import com.example.quizapp_fe.dialogs.LoadingDialog;
import com.example.quizapp_fe.entities.UserProfile;
import com.example.quizapp_fe.models.CredentialToken;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditProfileFragment extends Fragment {

    EditText edtFirstName;
    ImageView profileImage;
    EditText edtLastName;
    EditText edtUsername;
    Spinner spinnerUserType;
    LoadingDialog loadingDialog;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> pickImageLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);



        loadingDialog = new LoadingDialog(requireContext());

        ImageView btnBack = view.findViewById(R.id.btnBack);
        Button btnSave = view.findViewById(R.id.btnSave);

        profileImage = view.findViewById(R.id.profile_image);
        Button btnChangeProfilePic = view.findViewById(R.id.btnChangeProfilePic);
        edtFirstName = view.findViewById(R.id.edtFirstName);
        edtLastName = view.findViewById(R.id.edtLastName);
        edtUsername = view.findViewById(R.id.edtUsername);
        spinnerUserType = view.findViewById(R.id.spinnerUserType);
        final String[] profileImageUpload = {CredentialToken.getInstance(requireContext()).getUserProfile().getAvatar()};

        String[] items = new String[]{"Teacher", "Student"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, items);

        spinnerUserType.setAdapter(adapter);

        InitData();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnChangeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermission();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    checkAndRequestPermissionV33();
                }
            }
        });

        // Khởi tạo launcher để yêu cầu quyền
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                pickImage();
            }
        });

        Map config = new HashMap();
        config.put("cloud_name", "dfoiuc0jw");
        config.put("api_key", "293427824815773");
        config.put("api_secret", "a8h2AFQmgyegqDCbDhDiQESkuQg");
        config.put("secure", true);
        MediaManager.init(requireContext(), config);
        // Khởi tạo launcher để chọn hình ảnh
        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri selectedImageUri = result.getData().getData();
                if (selectedImageUri != null) {
                    System.out.println(selectedImageUri);
                    MediaManager.get().upload(selectedImageUri).option("folder", "quiz").callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {
                            loadingDialog.show();
                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {

                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            loadingDialog.dismiss();
                            String secureUrl = (String) resultData.get("secure_url");
                            profileImageUpload[0] = secureUrl;
                            Glide.with(requireContext())
                                    .load(resultData.get("secure_url"))
                                    .circleCrop()
                                    .into(profileImage);
                            Toast.makeText(requireContext(), "Upload success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            loadingDialog.dismiss();
                            Toast.makeText(requireContext(), "Upload failed", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {

                        }
                    }).dispatch();
                    profileImage.setImageURI(selectedImageUri);
                }
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidData()){
                    Toast.makeText(requireContext(), "Data is valid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private boolean isValidData(){
        if(edtFirstName.getText().toString().isEmpty()){
            edtFirstName.setError("First name is required");
            return false;
        }
        if(edtLastName.getText().toString().isEmpty()){
            edtLastName.setError("Last name is required");
            return false;
        }
        if(edtUsername.getText().toString().isEmpty()){
            edtUsername.setError("Username is required");
            return false;
        }
        return true;
    }

    void InitData(){
        UserProfile currentUser = CredentialToken.getInstance(requireContext()).getUserProfile();
        if(!Objects.equals(currentUser.getAvatar(), "")){
            Glide.with(requireContext())
                    .load(currentUser.getAvatar())
                    .circleCrop()
                    .into(profileImage);
        }
        edtFirstName.setText(currentUser.getFirstName());
        edtLastName.setText(currentUser.getLastName());
        edtUsername.setText(currentUser.getUsername());
        spinnerUserType.setSelection(currentUser.getUserType().equals("Teacher") ? 0 : 1);

    }

    private void checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            pickImage();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void checkAndRequestPermissionV33() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
        } else {
            pickImage();
        }
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

}
