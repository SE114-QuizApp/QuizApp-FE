package com.example.quizapp_fe.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.quizapp_fe.R;
import com.example.quizapp_fe.api.ErrorResponse;
import com.example.quizapp_fe.api.account.profile.UpdateProfileApi;
import com.example.quizapp_fe.dialogs.LoadingDialog;
import com.example.quizapp_fe.entities.UserProfile;
import com.example.quizapp_fe.models.CredentialToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.Objects;

public class EditProfileFragment extends Fragment {

    EditText edtFirstName;
    ImageView profileImage;
    EditText edtLastName;
    EditText edtUsername;
    Spinner spinnerUserType;
    LoadingDialog loadingDialog;
    String[] profileImageUpload;
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
        profileImageUpload = new String[]{CredentialToken.getInstance(requireContext()).getUserProfile().getAvatar()};

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
                if(!isDataChanged()){
                    Toast.makeText(requireContext(), "Data is not changed", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isValidData()){
                    return;
                }
                loadingDialog.showLoading();

                UserProfile userProfile = CredentialToken.getInstance(requireContext()).getUserProfile();
                String firstName = edtFirstName.getText().toString();
                String lastName = edtLastName.getText().toString();
                String username = edtUsername.getText().toString();
                String userType = spinnerUserType.getSelectedItem().toString();
                String avatar = profileImageUpload[0];


                UpdateProfileApi.getAPI(requireContext()).update(new UpdateProfileApi.API.EditProfileRequest(firstName, lastName, username, avatar, userType)).enqueue(new retrofit2.Callback<UpdateProfileApi.API.EditProfileResponse>() {
                    @Override
                    public void onResponse(@NonNull retrofit2.Call<UpdateProfileApi.API.EditProfileResponse> call, @NonNull retrofit2.Response<UpdateProfileApi.API.EditProfileResponse> response) {
                        if(response.isSuccessful()){
                            UpdateProfileApi.API.EditProfileResponse result = response.body();
                            assert result != null;
                            CredentialToken.getInstance(requireContext()).setUserProfile(result.getUser());

                            Toast.makeText(requireContext(), "Update success", Toast.LENGTH_SHORT).show();
                            loadingDialog.dismiss();
                        }else{
                            Gson gson = new GsonBuilder().create();
                            assert response.errorBody() != null;
                            ErrorResponse error = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                            loadingDialog.showError(error.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull retrofit2.Call<UpdateProfileApi.API.EditProfileResponse> call, @NonNull Throwable t) {
                        Toast.makeText(requireContext(), "Update failed", Toast.LENGTH_SHORT).show();
                    }
                });
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

    public boolean isDataChanged(){
        UserProfile currentUser = CredentialToken.getInstance(requireContext()).getUserProfile();
        if(!Objects.equals(currentUser.getAvatar(), profileImageUpload[0])){
            return true;
        }
        if(!Objects.equals(currentUser.getFirstName(), edtFirstName.getText().toString())){
            return true;
        }
        if(!Objects.equals(currentUser.getLastName(), edtLastName.getText().toString())){
            return true;
        }
        if(!Objects.equals(currentUser.getUsername(), edtUsername.getText().toString())){
            return true;
        }
        if(!Objects.equals(currentUser.getUserType(), spinnerUserType.getSelectedItem().toString())){
            return true;
        }
        return false;
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
