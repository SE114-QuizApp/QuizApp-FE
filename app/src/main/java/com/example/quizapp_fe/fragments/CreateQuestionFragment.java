package com.example.quizapp_fe.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.HomeActivity;
import com.example.quizapp_fe.adapters.QuestionIndexAdapter;
import com.example.quizapp_fe.api.ErrorResponse;
import com.example.quizapp_fe.api.quiz.create.CreateQuizApi;
import com.example.quizapp_fe.dialogs.AlertDialog;
import com.example.quizapp_fe.dialogs.ConfirmationDialog;
import com.example.quizapp_fe.dialogs.LoadingDialog;
import com.example.quizapp_fe.entities.Answer;
import com.example.quizapp_fe.entities.Question;
import com.example.quizapp_fe.entities.Quiz;
import com.example.quizapp_fe.models.CreateQuizViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateQuestionFragment extends Fragment {
    private RecyclerView questionIndexRecyclerView;
    private QuestionIndexAdapter questionIndexAdapter;
    private LinearLayout llDurationQuestion;
    private TextView tvDurationQuestion;
    private LinearLayout llQuestionType;
    private TextView tvQuestionType;
    private CreateQuizViewModel createQuizViewModel;
    private EditText etContent;
    private EditText etAnswerA;
    private EditText etAnswerB;
    private EditText etAnswerC;
    private EditText etAnswerD;
    private CheckBox cbCheckBoxA;
    private CheckBox cbCheckBoxB;
    private CheckBox cbCheckBoxC;
    private CheckBox cbCheckBoxD;
    ImageView btnMoreIcon;
    private Quiz quiz;
    private Question question;
    private ArrayList<Question> questionList;
    private LoadingDialog loadingDialog;
    private ConfirmationDialog confirmationDialog;
    private AlertDialog alertDialog;
    private TextView tvCheckboxError;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_question, container, false);

        createQuizViewModel = new ViewModelProvider(requireActivity()).get(CreateQuizViewModel.class);
        quiz = createQuizViewModel.getQuiz().getValue();

        etContent = view.findViewById(R.id.etContent);
        etAnswerA = view.findViewById(R.id.etAnswerA);
        etAnswerB = view.findViewById(R.id.etAnswerB);
        etAnswerC = view.findViewById(R.id.etAnswerC);
        etAnswerD = view.findViewById(R.id.etAnswerD);
        cbCheckBoxA = view.findViewById(R.id.cbCheckBoxA);
        cbCheckBoxB = view.findViewById(R.id.cbCheckBoxB);
        cbCheckBoxC = view.findViewById(R.id.cbCheckBoxC);
        cbCheckBoxD = view.findViewById(R.id.cbCheckBoxD);
        tvDurationQuestion = view.findViewById(R.id.tvDurationQuestion);
        tvQuestionType = view.findViewById(R.id.tvQuestionType);
        btnMoreIcon = view.findViewById(R.id.btnMoreIcon);
        llDurationQuestion = view.findViewById(R.id.durationQuestionContainer);
        llQuestionType = view.findViewById(R.id.questionTypeContainer);
        tvCheckboxError = view.findViewById(R.id.tvCheckboxError);
        Button btnSave = view.findViewById(R.id.btnSave);
        ImageView btnBack = view.findViewById(R.id.btnBackCreateQuestion);
        Button btnAddQuestion = view.findViewById(R.id.btnAddQuestion);

        loadingDialog = new LoadingDialog(requireActivity());
        confirmationDialog = new ConfirmationDialog(getActivity());
        alertDialog = new AlertDialog(getActivity());

        questionIndexRecyclerView = view.findViewById(R.id.questionIndexList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        questionIndexRecyclerView.setLayoutManager(layoutManager);

        questionList = quiz.getQuestionList();
        questionIndexAdapter = new QuestionIndexAdapter(questionList);

        if (questionList == null || questionList.isEmpty()) {
            handleAddNewQuestion();
            questionIndexAdapter.setSelectedPos(0);
        } else {
            questionIndexAdapter.setSelectedPos(questionList.size() - 1);

            int selectedQuestionPos = questionIndexAdapter.getSelectedPos().getValue();
            question = questionList.get(selectedQuestionPos);
            questionIndexRecyclerView.scrollToPosition(selectedQuestionPos);
        }

        questionIndexRecyclerView.setAdapter(questionIndexAdapter);

        createQuizViewModel.getQuiz().observe(getViewLifecycleOwner(), new Observer<Quiz>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(Quiz quiz) {
                questionIndexAdapter.setQuestions(quiz.getQuestionList());
                questionIndexAdapter.notifyDataSetChanged();
            }
        });

        questionIndexAdapter.setOnItemClickListener(new QuestionIndexAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                handleUpdateCurrentQuestion();
                quiz = createQuizViewModel.getQuiz().getValue();
                questionList = quiz.getQuestionList();
                question = questionList.get(position);
                handleUpdateInputs();
                clearFocusFromInputs();
            }
        });

//        questionIndexAdapter.getSelectedPos().observe(getViewLifecycleOwner(), new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer selectedQuestionPos) {
//                question = questionList.get(selectedQuestionPos);
//                handleUpdateInputs();
//            }
//        });

        llDurationQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDurationMenu();
            }
        });

        llQuestionType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuestionTypeMenu();
            }
        });

        // if question type is true / false, there should be only one checkbox checked
        CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (tvQuestionType.getText().toString().equals("True/False") && isChecked) {
                    if (buttonView.getId() == R.id.cbCheckBoxA) {
                        cbCheckBoxB.setChecked(false);
                    } else if (buttonView.getId() == R.id.cbCheckBoxB) {
                        cbCheckBoxA.setChecked(false);
                    }
                }
            }
        };
        cbCheckBoxA.setOnCheckedChangeListener(checkListener);
        cbCheckBoxB.setOnCheckedChangeListener(checkListener);

        btnMoreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), btnMoreIcon);
                popup.getMenuInflater().inflate(R.menu.more_options_create_question_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getTitle().toString()) {
                            case "Delete this question":
                                int selectedQuestionIndex = questionIndexAdapter.getSelectedPos().getValue() + 1;
                                confirmationDialog.show("Delete question", "Are you sure you want to delete question " + selectedQuestionIndex + "?", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteQuestion();
                                    }
                                }, null);

                                return true;
                            case "Discard changes":
                                confirmationDialog.show("Discard changes", "All changes will be discarded. Are you sure you want to exit?", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        discardChanges();
                                    }
                                }, null);

                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUpdateCurrentQuestion();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddQuestion();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUpdateCurrentQuestion();

                boolean isValidQuiz = isValidQuestions();

                if (!isValidQuiz) {
                    return;
                }

                confirmationDialog.show("Save quiz", "Are you sure you want to save quiz?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quiz = createQuizViewModel.getQuiz().getValue();
                        questionList = quiz.getQuestionList();
                        quiz.setNumberOfQuestions(questionList.size());
                        quiz.setDraft(false);

                        Gson gson = new Gson();
                        String jsonQuiz = gson.toJson(quiz);
                        Log.d("CreateQuestionFragment", jsonQuiz);

                        loadingDialog.showLoading("Creating quiz...");

                        CreateQuizApi.getAPI(getActivity()).createQuiz(new CreateQuizApi.API.CreateQuizRequest(quiz)).enqueue(new Callback<CreateQuizApi.API.CreateQuizResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<CreateQuizApi.API.CreateQuizResponse> call, @NonNull Response<CreateQuizApi.API.CreateQuizResponse> response) {
                                if (response.isSuccessful()) {
                                    CreateQuizApi.API.CreateQuizResponse createQuizResponse = response.body();
                                    assert createQuizResponse != null;
                                    Quiz quiz = createQuizResponse.getQuiz();

                                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                                    startActivity(intent);

                                    getActivity().finish();

                                    loadingDialog.dismiss();
                                    Toast.makeText(getActivity(), "Create quiz successfully", Toast.LENGTH_LONG).show();
                                } else {
                                    Gson gson = new GsonBuilder().create();
                                    assert response.errorBody() != null;
                                    ErrorResponse error = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                                    Log.d("CreateQuestionFragment", String.valueOf(error));
                                    loadingDialog.showError(error.getMessage());
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<CreateQuizApi.API.CreateQuizResponse> call, @NonNull Throwable t) {
                                Toast.makeText(getActivity(), "Create quiz failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }, null);
            }
        });

        return view;
    }

    private void handleCreateQuizApi() {
    }

    private void deleteQuestion() {
        int selectedQuestionPos = questionIndexAdapter.getSelectedPos().getValue();

        quiz.getQuestionList().remove(selectedQuestionPos);

        // update the question indexes
        questionList = quiz.getQuestionList();
        for (int i = selectedQuestionPos; i < questionList.size(); i++) {
            questionList.get(i).setQuestionIndex(i + 1);
        }

        questionIndexAdapter.notifyDataSetChanged();

        if (questionList.isEmpty()) {
            handleAddNewQuestion();
            questionIndexAdapter.setSelectedPos(0);
            question = questionList.get(0);

            handleUpdateInputs();
        } else if (selectedQuestionPos >= questionList.size()) {
            questionIndexAdapter.setSelectedPos(questionList.size() - 1);
            question = questionList.get(questionList.size() - 1);
            handleUpdateInputs();
        } else {
            questionIndexAdapter.setSelectedPos(selectedQuestionPos);
            question = questionList.get(selectedQuestionPos);
            handleUpdateInputs();
        }
    }

    private void discardChanges() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);

        getActivity().finish();
    }

    private void showDurationMenu() {
        PopupMenu popup = new PopupMenu(getActivity(), llDurationQuestion);
        popup.getMenuInflater().inflate(R.menu.duration_question_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                tvDurationQuestion.setText(item.getTitle());
                return true;
            }
        });
        popup.show();
    }

    private void showQuestionTypeMenu() {
        PopupMenu popup = new PopupMenu(getActivity(), llQuestionType);
        popup.getMenuInflater().inflate(R.menu.question_type_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                tvQuestionType.setText(item.getTitle());
                updateAnswerOptions(item.getTitle().toString().trim());

                return true;
            }
        });
        popup.show();
    }

    private void updateAnswerOptions(String questionType) {
        LinearLayout answersContainer = getView().findViewById(R.id.answersContainer);

        cbCheckBoxA.setChecked(false);
        cbCheckBoxB.setChecked(false);
        cbCheckBoxC.setChecked(false);
        cbCheckBoxD.setChecked(false);

        for (int i = 0; i < answersContainer.getChildCount(); i++) {
            LinearLayout answerOption = (LinearLayout) answersContainer.getChildAt(i);

            if (questionType.equals("True/False")) {
                if (i >= 2) {
                    answerOption.setVisibility(View.GONE);
                } else {
                    answerOption.setVisibility(View.VISIBLE);
                    if (i == 0) {
                        etAnswerA.setText("True");
                        etAnswerA.setEnabled(false);
                    } else if (i == 1) {
                        etAnswerB.setText("False");
                        etAnswerB.setEnabled(false);
                    }
                }
            } else {
                answerOption.setVisibility(View.VISIBLE);

                if (i < 2) {
                    etAnswerA.setText("");
                    etAnswerA.setEnabled(true);
                    etAnswerB.setText("");
                    etAnswerB.setEnabled(true);
                }
            }
        }

        etAnswerC.setText("");
        etAnswerD.setText("");
    }

    private void handleAddQuestion() {
        handleUpdateCurrentQuestion();
        handleAddNewQuestion();

        questionIndexAdapter.setSelectedPos(quiz.getQuestionList().size() - 1);

        handleUpdateInputs();
        clearFocusFromInputs();
        int selectedQuestionPos = questionIndexAdapter.getSelectedPos().getValue();
        questionIndexRecyclerView.scrollToPosition(selectedQuestionPos);
    }

    private void handleUpdateCurrentQuestion() {
        // get the question type from the TextView
        String questionType = tvQuestionType.getText().toString().trim();
        question.setQuestionType(questionType);

        // convert the duration text to an integer and set it to the question
        String durationText = tvDurationQuestion.getText().toString().trim();
        String[] parts = durationText.split(" ");
        int duration = Integer.parseInt(parts[0]);
        question.setAnswerTime(duration);

        // get the question content from the EditText
        String questionContent = etContent.getText().toString().trim();
        question.setContent(questionContent);

        ArrayList<Answer> answers = new ArrayList<>();
        ArrayList<String> answerCorrect = new ArrayList<>();

        // add answer list and set correct answers, max correct answers, and correct answer count
        if (questionType.equals("True/False")) {
            // name, body, íCorrect
            Answer answerA = new Answer("a", "True", false);
            Answer answerB = new Answer("b", "False", false);

            if (cbCheckBoxA.isChecked()) {
                answerA.setCorrect(true);
                answerCorrect.add("a");
            } else if (cbCheckBoxB.isChecked()) {
                answerB.setCorrect(true);
                answerCorrect.add("b");
            }

            answers.add(answerA);
            answers.add(answerB);

            question.setAnswerList(answers);
            question.setOptionQuestion("Single");
            question.setMaxCorrectAnswer(1);
            question.setCorrectAnswerCount(1);
        } else {
            int checkedCount = 0;

            // name, body, íCorrect
            Answer answerA = new Answer("a", etAnswerA.getText().toString().trim(), false);
            Answer answerB = new Answer("b", etAnswerB.getText().toString().trim(), false);
            Answer answerC = new Answer("c", etAnswerC.getText().toString().trim(), false);
            Answer answerD = new Answer("d", etAnswerD.getText().toString().trim(), false);

            if (cbCheckBoxA.isChecked()) {
                answerA.setCorrect(true);
                answerCorrect.add("a");
                checkedCount++;
            }

            if (cbCheckBoxB.isChecked()) {
                answerB.setCorrect(true);
                answerCorrect.add("b");
                checkedCount++;
            }

            if (cbCheckBoxC.isChecked()) {
                answerC.setCorrect(true);
                answerCorrect.add("c");
                checkedCount++;
            }

            if (cbCheckBoxD.isChecked()) {
                answerD.setCorrect(true);
                answerCorrect.add("d");
                checkedCount++;
            }

            if (checkedCount > 1) {
                question.setOptionQuestion("Multiple");
            } else {
                question.setOptionQuestion("Single");
            }

            answers.add(answerA);
            answers.add(answerB);
            answers.add(answerC);
            answers.add(answerD);

            question.setAnswerList(answers);
            question.setMaxCorrectAnswer(checkedCount);
            question.setCorrectAnswerCount(checkedCount);
        }

        question.setAnswerCorrect(answerCorrect);
        question.setAnswerList(answers);
        question.setTags(new ArrayList<String>());
        question.setPublic(true);
        question.setPointType("Standard");
        question.setBackgroundImage("");


        int questionIndex = question.getQuestionIndex();
        createQuizViewModel.updateQuestion(questionIndex - 1, question);
    }

    private void handleAddNewQuestion() {
        question = new Question();

        questionList = quiz.getQuestionList();

        if (questionList == null || questionList.isEmpty()) {
            question.setQuestionIndex(1);
        } else {
            question.setQuestionIndex(questionList.size() + 1);
        }

        createQuizViewModel.addQuestion(question);
        quiz = createQuizViewModel.getQuiz().getValue();
        questionList = quiz.getQuestionList();
        Toast.makeText(getActivity(), "Added question " + questionList.size(), Toast.LENGTH_SHORT).show();
    }

    private void handleUpdateInputs() {
        if (question.getQuestionType() != null && !question.getQuestionType().isEmpty()) {
            tvQuestionType.setText(question.getQuestionType());
            updateAnswerOptions(question.getQuestionType());
        } else {
            tvQuestionType.setText("Quiz");
            updateAnswerOptions("Quiz");
        }

        if (question.getAnswerTime() > 0) {
            tvDurationQuestion.setText(question.getAnswerTime() + " sec");
        } else {
            tvDurationQuestion.setText("5 sec");
        }

        if (question.getContent() != null && !question.getContent().isEmpty()) {
            etContent.setText(question.getContent());
        } else {
            etContent.setText("");
        }

        List<Answer> answers = question.getAnswerList();

        if (answers != null && !answers.isEmpty()) {
            for (Answer answer : answers) {
                switch (answer.getName()) {
                    case "a":
                        etAnswerA.setText(answer.getBody());
                        cbCheckBoxA.setChecked(answer.isCorrect());
                        break;
                    case "b":
                        etAnswerB.setText(answer.getBody());
                        cbCheckBoxB.setChecked(answer.isCorrect());
                        break;
                    case "c":
                        etAnswerC.setText(answer.getBody());
                        cbCheckBoxC.setChecked(answer.isCorrect());
                        break;
                    case "d":
                        etAnswerD.setText(answer.getBody());
                        cbCheckBoxD.setChecked(answer.isCorrect());
                        break;
                }
            }
        }
    }

    private boolean isValidQuestions() {
        ArrayList<Integer> invalidQuestionIndexes = new ArrayList<>();
        quiz = createQuizViewModel.getQuiz().getValue();
        questionList = quiz.getQuestionList();

        for (int i = 0; i < questionList.size(); i++) {
            Question question = questionList.get(i);

            boolean isContentFilled = !question.getContent().isEmpty();
            boolean isAtLeastOneCheckboxChecked = question.getAnswerList().stream().anyMatch(Answer::isCorrect);
            boolean areAllEditTextsFilled = true;

            if (question.getQuestionType().equals("Quiz")) {
                for (Answer answer : question.getAnswerList()) {
                    if (answer.getBody().isEmpty()) {
                        areAllEditTextsFilled = false;
                        break;
                    }
                }
            }

            if (!isContentFilled || !isAtLeastOneCheckboxChecked || !areAllEditTextsFilled) {
                invalidQuestionIndexes.add(i + 1);
            }
        }

        if (!invalidQuestionIndexes.isEmpty()) {
            // convert the list of invalid question indexes to a string and show a toast
            String invalidQuestionIndexesString = convertArrayToString(invalidQuestionIndexes);

            String strMoreThanOneInvalidQues = invalidQuestionIndexes.size() > 1 ? "are" : "is";

            alertDialog.show("Invalid questions", "Questions " + invalidQuestionIndexesString + " " + strMoreThanOneInvalidQues + " invalid. Please fill in all required fields.", null);

            int invalidQuestionIndex = invalidQuestionIndexes.get(0) - 1;
            questionIndexAdapter.setSelectedPos(invalidQuestionIndex);
            quiz = createQuizViewModel.getQuiz().getValue();
            questionList = quiz.getQuestionList();
            question = questionList.get(invalidQuestionIndex);
            handleUpdateInputs();

            if (question.getContent().isEmpty()) {
                etContent.setError("Question is required");
            }

            boolean isAnyCheckboxChecked = cbCheckBoxA.isChecked() || cbCheckBoxB.isChecked() || cbCheckBoxC.isChecked() || cbCheckBoxD.isChecked();
            if (!isAnyCheckboxChecked) {
                tvCheckboxError.setVisibility(View.VISIBLE);
            } else {
                tvCheckboxError.setVisibility(View.GONE);
            }

            for (Answer answer : question.getAnswerList()) {
                if (answer.getBody().isEmpty()) {
                    switch (answer.getName()) {
                        case "a":
                            etAnswerA.setError("Answer A is required");
                            break;
                        case "b":
                            etAnswerB.setError("Answer B is required");
                            break;
                        case "c":
                            etAnswerC.setError("Answer C is required");
                            break;
                        case "d":
                            etAnswerD.setError("Answer D is required");
                            break;
                    }
                }
            }

            return false;
        }

        return true;
    }

    private String convertArrayToString(ArrayList<Integer> list) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i));

            if (i < list.size() - 1) {
                stringBuilder.append(", ");
            }
        }

        return stringBuilder.toString();
    }

    private void clearFocusFromInputs() {
        etContent.clearFocus();
        etAnswerA.clearFocus();
        etAnswerB.clearFocus();
        etAnswerC.clearFocus();
        etAnswerD.clearFocus();
    }
}