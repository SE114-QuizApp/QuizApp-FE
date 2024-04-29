package com.example.quizapp_fe.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.CompoundButtonCompat;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.entities.Answer;
import com.example.quizapp_fe.entities.Question;
import com.example.quizapp_fe.entities.Quiz;
import com.example.quizapp_fe.models.JsonHelper;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PlayQuiz extends AppCompatActivity {
    private TextView txtCountDown;
    private TextView txtContentQuestion;

    private TextView txtIndexOfCurrentQuestion;
    private TextView txtContentQuestionNoImage;
    private ImageView questionImage;

    private Button btnPoint;
    private CircularProgressIndicator pgTimeRemaining;
    private LinearProgressIndicator pgQuestionRemaining;

    private LinearLayout titleAndImageQuestion;

    private CheckBox cbAnswerA;
    private CheckBox cbAnswerB;
    private CheckBox cbAnswerC;
    private CheckBox cbAnswerD;

    private LinearLayout lnAnswerA;
    private LinearLayout lnAnswerB;
    private LinearLayout lnAnswerC;
    private LinearLayout lnAnswerD;

    private LinearLayout lnAnswerGroup;

    private Quiz quiz;
    private ArrayList<Question> questionList;

    private int currentIndex = 0;

   private Question currentQuestion;

   private ArrayList<Answer> answersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.playQuizLinearLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Init
        titleAndImageQuestion = findViewById(R.id.titleAndImageQuestion);
        txtCountDown = findViewById(R.id.countDownTimeQuestion);
        txtContentQuestion = findViewById(R.id.contentOfQuestion);
        txtContentQuestionNoImage = findViewById(R.id.titleOfQuestionNoImage);
        txtIndexOfCurrentQuestion = findViewById(R.id.indexOfCurrentQuestion);
        pgTimeRemaining = findViewById(R.id.progressTimeRemaining);
        pgQuestionRemaining = findViewById(R.id.progressQuestionRemaining);
        questionImage = findViewById(R.id.questionImage);
        btnPoint = findViewById((R.id.pointOfQuestion));
        lnAnswerGroup = findViewById(R.id.answerGroup);

        cbAnswerA = findViewById(R.id.checkBox_a);
        cbAnswerB = findViewById(R.id.checkBox_b);
        cbAnswerC = findViewById(R.id.checkBox_c);
        cbAnswerD = findViewById(R.id.checkBox_d);

        lnAnswerA = findViewById(R.id.answerLayerNo0);
        lnAnswerB = findViewById(R.id.answerLayerNo1);
        lnAnswerC = findViewById(R.id.answerLayerNo2);
        lnAnswerD = findViewById(R.id.answerLayerNo3);

        changeStateCheckBox(lnAnswerA, cbAnswerA);
        changeStateCheckBox(lnAnswerB, cbAnswerB);
        changeStateCheckBox(lnAnswerC, cbAnswerC);
        changeStateCheckBox(lnAnswerD, cbAnswerD);

        // Đọc dữ liệu từ file json
        InputStream inputStream = getResources().openRawResource(R.raw.quiz);
        quiz = JsonHelper.loadQuizFromJson(inputStream);
        questionList = quiz.getQuestionList();

        pgQuestionRemaining.setMax((quiz.getNumberOfQuestions() * 10));

        displayQuestion(currentIndex);

        // Bắt đầu đếm ngược từ 10 giây
        startCountdown(currentQuestion.getAnswerTime() + 1);
    }

    private void displayQuestion(int currentQuestionIndex)
    {
        // Lấy dữ liệu từng câu question và answer
        currentQuestion = questionList.get(currentQuestionIndex);
        answersList = currentQuestion.getAnswerList();

        resetCbState();


        // set init value
        pgQuestionRemaining.setProgress((currentQuestionIndex + 1) * 10, true);
        btnPoint.setText("0" + quiz.getPointsPerQuestion());
        txtIndexOfCurrentQuestion.setText("QUESTION " + (currentQuestionIndex + 1) + " OF " + quiz.getNumberOfQuestions());
        txtContentQuestion.setText(currentQuestion.getContent());

        if(currentQuestion.getBackgroundImage().equals("")) {
            ViewGroup parent = (ViewGroup) titleAndImageQuestion.getParent();
            int index = parent.indexOfChild(titleAndImageQuestion);
            // Nếu không có Image, ẩn LinearLayout
            titleAndImageQuestion.setVisibility(View.GONE);

            // Cho hiện TextView không image
            txtContentQuestionNoImage.setVisibility(View.VISIBLE);
            txtContentQuestionNoImage.setText(currentQuestion.getContent());
        }
        else {
            File imgFile = new  File(currentQuestion.getBackgroundImage());
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            questionImage.setImageBitmap(bitmap);
        }

        // Nếu số answer == 2 hoặc 4
        if(answersList.size() == 2) {

            // Ẩn 2 đáp án sau
            lnAnswerC.setVisibility(View.GONE);
            lnAnswerD.setVisibility(View.GONE);

            // set answer
            cbAnswerA.setText(answersList.get(0).getBody());
            cbAnswerB.setText(answersList.get(1).getBody());
        }
        else if(answersList.size() == 4)
        {
            lnAnswerC.setVisibility(View.VISIBLE);
            lnAnswerD.setVisibility(View.VISIBLE);

            // set answer
            cbAnswerA.setText(answersList.get(0).getBody());
            cbAnswerB.setText(answersList.get(1).getBody());
            cbAnswerC.setText(answersList.get(2).getBody());
            cbAnswerD.setText(answersList.get(3).getBody());
        }
        else {
            // do nothing
        }
    }

    private void resetCbState() {
        for (int i = 0; i < lnAnswerGroup.getChildCount(); i++) {
            View childView = lnAnswerGroup.getChildAt(i);
            if (childView instanceof LinearLayout) {
                LinearLayout otherAnswerLayout = (LinearLayout) childView;
                CheckBox otherCheckBox = (CheckBox) otherAnswerLayout.getChildAt(0);
                otherCheckBox.setEnabled(true);
                otherCheckBox.setChecked(false);
                int color = ContextCompat.getColor(getBaseContext(), R.color.dull_lavender);
                ColorStateList colorStateList = ColorStateList.valueOf(color);
                CompoundButtonCompat.setButtonTintList(otherCheckBox, colorStateList);
            }
        }
    }

    private void startCountdown(long seconds) {
        pgTimeRemaining.setProgress(100, true);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(pgTimeRemaining, "progress", 0);
        progressAnimator.setDuration(seconds * 1000);
        progressAnimator.start();
        new CountDownTimer(seconds * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                // Cập nhật giá trị đếm ngược trên TextView
                txtCountDown.setText(millisUntilFinished / 1000 + "");
            }

            public void onFinish() {
                // Đếm ngược hoàn thành
                txtCountDown.setText("0");

                if(currentIndex < quiz.getNumberOfQuestions() - 1) {
                    currentIndex += 1;
                    displayQuestion(currentIndex);
                    startCountdown(currentQuestion.getAnswerTime() + 1);
                }
                else {
                    // Nếu không còn câu hỏi nào navigate sang review answers
                    Intent intent = new Intent(PlayQuiz.this, ReviewAnswers.class);
                    startActivity(intent);
                }
            }

        }.start();
    }

    private void changeStateCheckBox(LinearLayout ln, CheckBox cb) {
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Drawable roundedDrawable = ContextCompat.getDrawable(getBaseContext(), R.drawable.answer_rounded);
                    ln.setBackground(roundedDrawable);

                    // set color
                    int color = ContextCompat.getColor(getBaseContext(), R.color.purple_hue);
                    ColorStateList colorStateList = ColorStateList.valueOf(color);
                    CompoundButtonCompat.setButtonTintList(cb, colorStateList);

                    // Kiểm tra đáp án
                    checkAnswer(ln, cb);

                    // Tắt các CheckBox khác
                    disableOtherCheckBoxes(ln, cb);
                } else {
                    Drawable roundedFalseDrawable = ContextCompat.getDrawable(getBaseContext(), R.drawable.answer_rounded_false);
                    ln.setBackground(roundedFalseDrawable);
                    // set color
                    int color = ContextCompat.getColor(getBaseContext(), R.color.dull_lavender);
                    ColorStateList colorStateList = ColorStateList.valueOf(color);
                    CompoundButtonCompat.setButtonTintList(cb, colorStateList);
                }
            }
        });
    }

    private void checkAnswer(LinearLayout linearLayout, CheckBox checkBox) {
        String selectedAnswer = checkBox.getText().toString();
        boolean isCorrect = false;

        for (Answer answer : answersList) {
            if (answer.getBody().equals(selectedAnswer) && answer.isCorrect()) {
                isCorrect = true;
                break;
            }
        }

        if (isCorrect) {
            Drawable trueDrawable = ContextCompat.getDrawable(getBaseContext(), R.drawable.bg_answer_checked_true);
            linearLayout.setBackground(trueDrawable);
            int color = ContextCompat.getColor(getBaseContext(), R.color.green_lime);
            ColorStateList colorStateList = ColorStateList.valueOf(color);
            CompoundButtonCompat.setButtonTintList(checkBox, colorStateList);
        } else {
            Drawable falseDrawable = ContextCompat.getDrawable(getBaseContext(), R.drawable.border_20dp_salmon_pink);
            linearLayout.setBackground(falseDrawable);
            int color = ContextCompat.getColor(getBaseContext(), R.color.pastel_pink);
            ColorStateList colorStateList = ColorStateList.valueOf(color);
            CompoundButtonCompat.setButtonTintList(checkBox, colorStateList);
        }
    }

    private void disableOtherCheckBoxes(LinearLayout selectedAnswerLayout, CheckBox selectedAnswerCb) {
        for (int i = 0; i < lnAnswerGroup.getChildCount(); i++) {
            View childView = lnAnswerGroup.getChildAt(i);
            if (childView instanceof LinearLayout && childView != selectedAnswerLayout) {
                LinearLayout otherAnswerLayout = (LinearLayout) childView;
                CheckBox otherCheckBox = (CheckBox) otherAnswerLayout.getChildAt(0);
                otherCheckBox.setEnabled(false);
                int color = ContextCompat.getColor(getBaseContext(), R.color.mecha_metal);
                ColorStateList colorStateList = ColorStateList.valueOf(color);
                CompoundButtonCompat.setButtonTintList(otherCheckBox, colorStateList);
            }
        }
    }
}