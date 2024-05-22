package com.example.quizapp_fe.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.HomeActivity;
import com.example.quizapp_fe.adapters.QuestionIndexAdapter;
import com.example.quizapp_fe.entities.Answer;
import com.example.quizapp_fe.entities.Question;
import com.example.quizapp_fe.entities.Quiz;
import com.example.quizapp_fe.models.CreateQuizViewModel;

import java.util.ArrayList;
import java.util.List;

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
            question = questionList.get(questionIndexAdapter.getSelectedPos().getValue());
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
                question = questionList.get(position);
                handleUpdateInputs();
            }
        });

//        questionIndexAdapter.getSelectedPos().observe(getViewLifecycleOwner(), new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer selectedQuestionPos) {
//                question = questionList.get(selectedQuestionPos);
//                handleUpdateInputs();
//            }
//        });

        // get the durationQuestionContainer and set an onClickListener
        llDurationQuestion = view.findViewById(R.id.durationQuestionContainer);
        llDurationQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDurationMenu();
            }
        });

        llQuestionType = view.findViewById(R.id.questionTypeContainer);
        llQuestionType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuestionTypeMenu();
            }
        });


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

        btnMoreIcon = view.findViewById(R.id.btnMoreIcon);
        btnMoreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), btnMoreIcon);
                popup.getMenuInflater().inflate(R.menu.popup_menu_more_icon_create_question_fragment, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getTitle().toString()) {
                            case "Delete this question":
                                deleteQuestion();
                                return true;
                            case "Discard changes":
                                discardChanges();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });

        ImageView btnBack = view.findViewById(R.id.btnBackCreateQuestion);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUpdateCurrentQuestion();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        Button btnAddQuestion = view.findViewById(R.id.btnAddQuestion);
        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddQuestion();
            }
        });

        Button btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUpdateCurrentQuestion();

                boolean isValidQuiz = isValidQuestions();

                if (!isValidQuiz) {
                    return;
                }

                questionList = quiz.getQuestionList();
                quiz.setNumberOfQuestions(questionList.size());
                quiz.setDraft(false);

//                createQuizViewModel.clearQuiz();

                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);

                getActivity().finish();

                Toast.makeText(getActivity(), "Create quiz successfully", Toast.LENGTH_LONG).show();
            }
        });

        return view;
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

        Toast.makeText(getActivity(), "Delete question successfully", Toast.LENGTH_SHORT).show();

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

        Toast.makeText(getActivity(), "Exit creating quiz", Toast.LENGTH_SHORT).show();
    }

    private void showDurationMenu() {
        PopupMenu popup = new PopupMenu(getActivity(), llDurationQuestion);
        popup.getMenuInflater().inflate(R.menu.popup_menu_duration_question, popup.getMenu());
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
        popup.getMenuInflater().inflate(R.menu.popup_menu_question_type, popup.getMenu());
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
        questionList = quiz.getQuestionList();
        Toast.makeText(getActivity(), "Question " + questionList.size() + " is added", Toast.LENGTH_SHORT).show();
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

    private void resetInputs() {
        String questtionType = "Quiz";
        tvQuestionType.setText(questtionType);
        updateAnswerOptions(questtionType);

        tvDurationQuestion.setText("5 sec");

        etContent.setText("");

        etAnswerA.setText("");
        etAnswerB.setText("");
        etAnswerC.setText("");
        etAnswerD.setText("");

        cbCheckBoxA.setChecked(false);
        cbCheckBoxB.setChecked(false);
        cbCheckBoxC.setChecked(false);
        cbCheckBoxD.setChecked(false);
    }

    private boolean isValidQuestions() {
        List<Integer> invalidQuestionIndexes = new ArrayList<>();
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
            String invalidQuestionIndexesString = "";
            for (int i = 0; i < invalidQuestionIndexes.size(); i++) {
                invalidQuestionIndexesString += invalidQuestionIndexes.get(i);

                if (i < invalidQuestionIndexes.size() - 1) {
                    invalidQuestionIndexesString += ", ";
                }
            }
            Toast.makeText(getActivity(), "Invalid questions: " + invalidQuestionIndexesString, Toast.LENGTH_SHORT).show();

            int invalidQuestionIndex = invalidQuestionIndexes.get(0) - 1;
            questionIndexAdapter.setSelectedPos(invalidQuestionIndex);
            question = questionList.get(invalidQuestionIndex);
            handleUpdateInputs();

            if (question.getContent().isEmpty()) {
                etContent.setError("Question is required");
            }

            boolean isAnyCheckboxChecked = cbCheckBoxA.isChecked() || cbCheckBoxB.isChecked() || cbCheckBoxC.isChecked() || cbCheckBoxD.isChecked();
            if (!isAnyCheckboxChecked) {
                Toast.makeText(getActivity(), "At least one checkbox needs to be checked", Toast.LENGTH_SHORT).show();
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
}