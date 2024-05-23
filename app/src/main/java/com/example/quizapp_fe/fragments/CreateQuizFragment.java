package com.example.quizapp_fe.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.HomeActivity;
import com.example.quizapp_fe.dialogs.ConfirmationDialog;
import com.example.quizapp_fe.dialogs.LoadingDialog;
import com.example.quizapp_fe.entities.Grade;
import com.example.quizapp_fe.entities.Quiz;
import com.example.quizapp_fe.models.CreateQuizViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateQuizFragment extends Fragment {
    private Quiz quiz;
    private ImageView quizBackgroundImage;
    private TextView tvGrade;
    private LinearLayout gradeContainer;
    private CreateQuizViewModel createQuizViewModel;
    private EditText etQuizName;
    private SwitchCompat switchPublicQuiz;
    private EditText etQuizPointsPerQuestion;
    private EditText etQuizTags;
    private EditText etQuizDescription;
    private ImageView btnMoreIcon;
    private LoadingDialog loadingDialog;
    private Uri selectedImageUri;
    private ConfirmationDialog confirmationDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_quiz, container, false);

        etQuizName = view.findViewById(R.id.etQuizName);
        switchPublicQuiz = view.findViewById(R.id.switchPublicQuiz);
        etQuizPointsPerQuestion = view.findViewById(R.id.etQuizPointsPerQuestion);
        etQuizTags = view.findViewById(R.id.etQuizTags);
        etQuizDescription = view.findViewById(R.id.etQuizDescription);
        tvGrade = view.findViewById(R.id.tvGrade);
        gradeContainer = view.findViewById(R.id.gradeContainer);
        btnMoreIcon = view.findViewById(R.id.btnMoreIcon);
        ImageView btnBack = view.findViewById(R.id.btnBackCreateQuiz);
        Button btnAddQuestions = view.findViewById(R.id.btnAddQuestions);
        quizBackgroundImage = view.findViewById(R.id.quizBackgroundImage);
        Button btnChooseQuizBackgroundImage = view.findViewById(R.id.btnChooseQuizBackgroundImage);

        confirmationDialog = new ConfirmationDialog(requireContext());

        // get the quiz object from the ViewModel
        createQuizViewModel = new ViewModelProvider(requireActivity()).get(CreateQuizViewModel.class);
        quiz = createQuizViewModel.getQuiz().getValue();

        // set the values of the quiz object to the UI elements
        if (quiz != null) {
            etQuizName.setText(quiz.getName());
            switchPublicQuiz.setChecked(quiz.isPublic());
            tvGrade.setText(String.valueOf(quiz.getGrade().getName()));
            etQuizTags.setText(String.join(", ", quiz.getTags()));
            etQuizDescription.setText(quiz.getDescription());

            if (quiz.getPointsPerQuestion() > 0) {
                etQuizPointsPerQuestion.setText(String.valueOf(quiz.getPointsPerQuestion()));
            }

            // set the background image of the quiz if it exists
            if (quiz.getBackgroundImage() != null && !quiz.getBackgroundImage().isEmpty()) {
                byte[] decodedString = Base64.decode(quiz.getBackgroundImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                quizBackgroundImage.setImageBitmap(decodedByte);
            }
        }

        gradeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), v);
                popupMenu.getMenuInflater().inflate(R.menu.grade_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        tvGrade.setText(item.getTitle());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        btnMoreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), v);
                popupMenu.getMenuInflater().inflate(R.menu.more_options_create_quiz_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getTitle().toString()) {
                            case "Discard changes":
                                confirmationDialog.show("Discard changes", "All changes will be discarded. Are you sure you want to exit?", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                }, null);

                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();
            }
        });

        btnAddQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = checkValidation();

                if (!isValid) {
                    return;
                }

                updateQuiz();

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.creatorQuizMainContainerFrameLayout, new CreateQuestionFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuiz();

                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnChooseQuizBackgroundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermission();
            }
        });

        return view;
    }

    // update the quiz object
    private void updateQuiz() {
        if (selectedImageUri != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                byte[] byteArray = outputStream.toByteArray();
                String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                quiz.setBackgroundImage(encodedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        quiz.setName(etQuizName.getText().toString().trim());
        quiz.setPublic(switchPublicQuiz.isChecked());
        quiz.setGrade(new Grade(tvGrade.getText().toString().trim()));

        if (!etQuizPointsPerQuestion.getText().toString().isEmpty()) {
            quiz.setPointsPerQuestion(Integer.parseInt(etQuizPointsPerQuestion.getText().toString().trim()));
        }

        // convert the tags from UI string to a list of tags
        String tagsString = etQuizTags.getText().toString().trim();
        ArrayList<String> tags = new ArrayList<>(Arrays.asList(tagsString.split(", ")));
        quiz.setTags(tags);

        quiz.setDescription(etQuizDescription.getText().toString().trim());

        createQuizViewModel.setQuiz(quiz);
    }

    Boolean checkValidation() {
        boolean isValid = true;

        if (etQuizName.getText().toString().isEmpty()) {
            etQuizName.setError("Title is required");
            isValid = false;
        }
        if (!etQuizPointsPerQuestion.getText().toString().isEmpty() && !etQuizPointsPerQuestion.getText().toString().matches("[0-9]+")) {
            etQuizPointsPerQuestion.setError("Points per question must be a number");
            isValid = false;
        }
        if (etQuizPointsPerQuestion.getText().toString().isEmpty()) {
            etQuizPointsPerQuestion.setError("Points per question is required");
            isValid = false;
        } else if (!etQuizPointsPerQuestion.getText().toString().matches("[0-9]+")) {
            etQuizPointsPerQuestion.setError("Points per question must be a number");
            isValid = false;
        }

        return isValid;
    }

    private void checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            pickImage();
        }
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        selectedImageUri = result.getData().getData();
                        quizBackgroundImage.setImageURI(selectedImageUri);
                    }
                }
            }
    );

    ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean isGranted) {
                    if (isGranted) {
                        pickImage();
                    } else {
                        Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
}