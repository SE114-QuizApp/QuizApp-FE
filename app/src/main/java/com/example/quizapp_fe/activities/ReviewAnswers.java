package com.example.quizapp_fe.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
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
import com.example.quizapp_fe.models.UserAnswers;

import java.util.ArrayList;

public class ReviewAnswers extends AppCompatActivity {

    private TextView txtNoCorrect;
    private TextView txtDescCorrect;
    private TextView txtTotalPoint;
    private LinearLayout lnAnswerReviewGroup;
    private ArrayList<Answer> userAnswerList;
    private ArrayList<Question> questionList;
    private int totalPoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_answers);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.reviewAnswersLinearLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Init
        txtNoCorrect = findViewById(R.id.numberOfCorrectAnswer);
        txtDescCorrect = findViewById(R.id.descripCorrectAnswer);
        txtTotalPoint = findViewById(R.id.displayTotalPoints);
        lnAnswerReviewGroup = findViewById(R.id.answerReviewGroup);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        UserAnswers reviewAnswer = (UserAnswers) intent.getSerializableExtra("userAnswers");
        userAnswerList = reviewAnswer.getUserAnswersList();
        questionList = reviewAnswer.getQuestionsList();
        totalPoints = reviewAnswer.getTotalPoints();

        int cntCorrect = 0;
        for (Answer answer : userAnswerList) {
            if (answer.isCorrect()) {
                cntCorrect++;
            }
        }
        txtNoCorrect.setText(cntCorrect + "/" + userAnswerList.size());
        txtDescCorrect.setText("You answer " + cntCorrect + "\nout of " + userAnswerList.size() + "\nquestions");
        txtTotalPoint.setText("Total Points: " + totalPoints);

        for (int i = 0; i < questionList.size(); i++) {
            lnAnswerReviewGroup.addView(rowLinearQuestion(questionList.get(i), userAnswerList.get(i)), i);
        }
    }

    public LinearLayout rowLinearQuestion(Question question, Answer userAnswer) {
        LinearLayout questionSummaryLinearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(55, 70, 55, 70);
        questionSummaryLinearLayout.setLayoutParams(params);
        questionSummaryLinearLayout.setOrientation(LinearLayout.VERTICAL);

        // Tạo FrameLayout bên trong LinearLayout
        FrameLayout frameLayout = new FrameLayout(this);
        LinearLayout.LayoutParams frameLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        frameLayout.setLayoutParams(frameLayoutParams);
        frameLayout.setPadding(0, 0, 0, 20);

        // Tạo Button
        Button btnOrder = new Button(this);
        FrameLayout.LayoutParams btnLayoutParams = new FrameLayout.LayoutParams(
                110, 110
        );
        btnLayoutParams.gravity = Gravity.START;
        btnOrder.setLayoutParams(btnLayoutParams);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(160); // Đặt góc cong
        gradientDrawable.setColor(getResources().getColor(R.color.white));
        btnOrder.setBackground(gradientDrawable);
        btnOrder.setText(String.valueOf(question.getQuestionIndex()));
        btnOrder.setTextColor(getResources().getColor(R.color.purple_hue));
        btnOrder.setTextSize(16);
        btnOrder.setTypeface(null, Typeface.BOLD);
        btnOrder.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));

        // Tạo TextView
        TextView txtQuestion = new TextView(this);
        FrameLayout.LayoutParams txtLayoutParams = new FrameLayout.LayoutParams(
                600,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        txtLayoutParams.leftMargin = 40;
        txtLayoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL; // Đặt vị trí của TextView
        txtQuestion.setLayoutParams(txtLayoutParams);
        txtQuestion.setText(question.getContent());
        txtQuestion.setTextSize(16);
        txtQuestion.setTypeface(null, Typeface.BOLD);
        txtQuestion.setTextColor(getResources().getColor(R.color.black));


        // Thêm Button, TextView vào FrameLayout
        frameLayout.addView(btnOrder);
        frameLayout.addView(txtQuestion);

        // Tạo ImageView
        if (!userAnswer.isCorrect()) {
            ImageView imgError = new ImageView(this);
            FrameLayout.LayoutParams imgLayoutParams = new FrameLayout.LayoutParams(
                    70, // Kích thước của ImageView
                    FrameLayout.LayoutParams.MATCH_PARENT
            );
            imgLayoutParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL; // Đặt vị trí của ImageView
            imgError.setLayoutParams(imgLayoutParams);
            imgError.setImageResource(R.drawable.ic_error_outline); // Đặt hình ảnh
            frameLayout.addView(imgError);
        } else {
            ImageView imgError = new ImageView(this);
            FrameLayout.LayoutParams imgLayoutParams = new FrameLayout.LayoutParams(
                    70, // Kích thước của ImageView
                    FrameLayout.LayoutParams.MATCH_PARENT
            );
            imgLayoutParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL; // Đặt vị trí của ImageView
            imgError.setLayoutParams(imgLayoutParams);
            imgError.setImageResource(R.drawable.ic_true_answer); // Đặt hình ảnh
            frameLayout.addView(imgError);
        }

        // Tạo TextView bên ngoài FrameLayout
        TextView txtAnswer = new TextView(this);
        LinearLayout.LayoutParams txtAnswerLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        txtAnswerLayoutParams.leftMargin = 160;
        txtAnswerLayoutParams.bottomMargin = 0;
        txtAnswer.setLayoutParams(txtAnswerLayoutParams);
        txtAnswer.setText("- " + userAnswer.getBody());
        txtAnswer.setTextSize(16);

        // Thêm FrameLayout và TextView vào LinearLayout
        questionSummaryLinearLayout.addView(frameLayout);
        questionSummaryLinearLayout.addView(txtAnswer);

        return questionSummaryLinearLayout;
    }
}