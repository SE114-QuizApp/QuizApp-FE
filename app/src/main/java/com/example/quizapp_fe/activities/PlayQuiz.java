package com.example.quizapp_fe.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

    private CircularProgressIndicator pgTimeRemaining;
    private LinearProgressIndicator pgQuestionRemaining;

    private LinearLayout titleAndImageQuestion;

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
        // Lấy dữ liệu từng câu question
        currentQuestion = questionList.get(currentQuestionIndex);
        answersList = currentQuestion.getAnswerList();

        pgQuestionRemaining.setProgress((currentQuestionIndex + 1) * 10, true);
        txtIndexOfCurrentQuestion.setText("QUESTION " + (currentQuestionIndex + 1) + " OF " + quiz.getNumberOfQuestions());
        txtContentQuestion.setText(currentQuestion.getContent());

        if(currentQuestion.getBackgroundImage().equals("")) {
            ViewGroup parent = (ViewGroup) titleAndImageQuestion.getParent();
            int index = parent.indexOfChild(titleAndImageQuestion);
            // Nếu không có Image, ẩn LinearLayout
            titleAndImageQuestion.setVisibility(View.GONE);

            // Tạo TextView mới
            txtContentQuestionNoImage.setVisibility(View.VISIBLE);
            txtContentQuestionNoImage.setText(currentQuestion.getContent());
        }
        else {
            File imgFile = new  File(currentQuestion.getBackgroundImage());
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            questionImage.setImageBitmap(bitmap);
        }
    }

    private void resetContent()
    {
        txtContentQuestion.setText("");
        File imgFile = new  File("");
        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        questionImage.setImageBitmap(bitmap);
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
}