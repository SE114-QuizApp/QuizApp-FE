package com.example.quizapp_fe.fragments;

import android.Manifest;
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
import com.example.quizapp_fe.entities.Grade;
import com.example.quizapp_fe.entities.Quiz;
import com.example.quizapp_fe.models.CreateQuizViewModel;

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

    private final ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                        quizBackgroundImage.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    mGetContent.launch("image/*");
                } else {
                    Toast.makeText(getActivity(), "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_quiz, container, false);

        quizBackgroundImage = view.findViewById(R.id.quizBackgroundImage);
        Button btnChooseQuizBackgroundImage = view.findViewById(R.id.btnChooseQuizBackgroundImage);

        btnChooseQuizBackgroundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                } else {
                    mGetContent.launch("image/*");
                }
            }
        });

        etQuizName = view.findViewById(R.id.etQuizName);
        switchPublicQuiz = view.findViewById(R.id.switchPublicQuiz);
        etQuizPointsPerQuestion = view.findViewById(R.id.etQuizPointsPerQuestion);
        etQuizTags = view.findViewById(R.id.etQuizTags);
        etQuizDescription = view.findViewById(R.id.etQuizDescription);
        tvGrade = view.findViewById(R.id.tvGrade);
        gradeContainer = view.findViewById(R.id.gradeContainer);


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
            if (!quiz.getBackgroundImage().isEmpty()) {
                byte[] imageBytes = Base64.decode(quiz.getBackgroundImage(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                quizBackgroundImage.setImageBitmap(bitmap);
            }
        }

        gradeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGradeMenu(v);
            }
        });

        btnMoreIcon = view.findViewById(R.id.btnMoreIcon);

        btnMoreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), v);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_more_icon_create_quiz_fragment, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getTitle().toString()) {
                            case "Discard changes":
//                                createQuizViewModel.clearQuiz();

                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                startActivity(intent);
                                getActivity().finish();

                                Toast.makeText(getActivity(), "Exit creating quiz", Toast.LENGTH_SHORT).show();

                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();
            }
        });


        ImageView btnBack = view.findViewById(R.id.btnBackCreateQuiz);
        Button btnAddQuestions = view.findViewById(R.id.btnAddQuestions);

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

        return view;
    }

    // add values to the grade menu
    private void showGradeMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        for (int i = 1; i <= 12; i++) {
            popupMenu.getMenu().add(String.valueOf(i));
        }

        // add the "All" option to the menu
        popupMenu.getMenu().add("All");
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                tvGrade.setText(item.getTitle());
                return true;
            }
        });
        popupMenu.show();
    }

    // update the quiz object
    private void updateQuiz() {
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

        // set the background image of the quiz (error here)
        if (quizBackgroundImage.getDrawable() != null) {
//            Bitmap bitmap = ((BitmapDrawable) quizBackgroundImage.getDrawable()).getBitmap();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] imageBytes = baos.toByteArray();
//            String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//            quiz.setBackgroundImage(imageString);
        } else {
            quiz.setBackgroundImage("");
        }

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