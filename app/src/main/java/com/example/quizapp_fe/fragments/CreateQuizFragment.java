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
import androidx.lifecycle.Observer;
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
    private ConfirmationDialog confirmationDialog;
    private TextView tvHeading;

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
        tvHeading = view.findViewById(R.id.tvHeading);
        ImageView btnBack = view.findViewById(R.id.btnBackCreateQuiz);
        Button btnNext = view.findViewById(R.id.btnNext);
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

            if (quiz.getGrade() != null && quiz.getGrade().getName() != null && !quiz.getGrade().getName().isEmpty()) {
                tvGrade.setText(quiz.getGrade().getName());
            } else {
                tvGrade.setText("All");
            }

            if (quiz.getPointsPerQuestion() > 0) {
                etQuizPointsPerQuestion.setText(String.valueOf(quiz.getPointsPerQuestion()));
            }

            etQuizTags.setText(String.join(", ", quiz.getTags()));
            etQuizDescription.setText(quiz.getDescription());
        }

        createQuizViewModel.isUpdate().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isUpdate) {
                if (isUpdate) {
                    tvHeading.setText("Update Quiz");
                } else {
                    tvHeading.setText("Create Quiz");
                }
            }
        });

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

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = checkValidation();

                if (!isValid) {
                    return;
                }

                updateQuiz();

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.creatorQuizContainer, new CreateQuestionFragment());
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

            }
        });

        return view;
    }

    // update the quiz object
    private void updateQuiz() {
        quiz.setName(etQuizName.getText().toString().trim());
        quiz.setPublic(switchPublicQuiz.isChecked());

        if (quiz.getGrade() != null) {
            quiz.getGrade().setName(tvGrade.getText().toString().trim());
        } else {
            quiz.setGrade(new Grade(tvGrade.getText().toString().trim()));
        }

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
}