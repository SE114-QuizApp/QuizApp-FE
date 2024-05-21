package com.example.quizapp_fe.activities;

import static androidx.core.content.ContentProviderCompat.requireContext;

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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.api.ErrorResponse;
import com.example.quizapp_fe.api.account.profile.UpdateProfileApi;
import com.example.quizapp_fe.api.user.updateUserPoint.UpdateUserPointApi;
import com.example.quizapp_fe.dialogs.LoadingDialog;
import com.example.quizapp_fe.entities.Answer;
import com.example.quizapp_fe.entities.Question;
import com.example.quizapp_fe.entities.UserProfile;
import com.example.quizapp_fe.models.CredentialToken;
import com.example.quizapp_fe.models.UserAnswers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;

public class ReviewAnswers extends AppCompatActivity {

    private TextView txtNoCorrect;
    private TextView txtDescCorrect;
    private TextView txtQuizName;
    LoadingDialog loadingDialog;

    private TextView txtTotalPoint;
    private Button btnSubmitAnswer;
    private Button btnPlayAgain;
    private Button btnBackHome;
    private LinearLayout lnAnswerReviewGroup;
    private ArrayList<Question> userQuestionAnswerList;
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
        txtQuizName = findViewById(R.id.quizName);
        txtTotalPoint = findViewById(R.id.displayTotalPoints);
        lnAnswerReviewGroup = findViewById(R.id.answerReviewGroup);
        btnSubmitAnswer = findViewById(R.id.submitAnswer);
        btnPlayAgain = findViewById(R.id.playQuizAgain);
        btnBackHome = findViewById(R.id.backToHome);

        loadingDialog = new LoadingDialog(ReviewAnswers.this);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        UserAnswers reviewAnswer = (UserAnswers) intent.getSerializableExtra("userAnswers");
        String quizId = (String) intent.getSerializableExtra("quizId");
        String quizName = (String) intent.getSerializableExtra("quizName");
        int numberOfQuestions = (int) intent.getSerializableExtra("numberOfQuestions");
        userQuestionAnswerList = reviewAnswer.getUserQuestionList();
        totalPoints = reviewAnswer.getTotalPoints();

        int cntCorrect = 0;
        for (Question question : userQuestionAnswerList) {
            for (Answer answer : question.getAnswerList()) {
                if (!answer.getName().equals("")) {
                    cntCorrect += 1;
                    break;
                }
            }
        }
        txtQuizName.setText(quizName);
        txtNoCorrect.setText(cntCorrect + "/" + numberOfQuestions);
        txtDescCorrect.setText("You answered " + cntCorrect + " out of " + numberOfQuestions + " questions");
        txtTotalPoint.setText("Total Points: " + totalPoints);

        for (int i = 0; i < userQuestionAnswerList.size(); i++) {
            lnAnswerReviewGroup.addView(rowLinearQuestion(userQuestionAnswerList.get(i), userQuestionAnswerList.get(i).getAnswerList()), i);
        }

        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewAnswers.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewAnswers.this, PlayQuiz.class);
                intent.putExtra("quizId", quizId);
                startActivity(intent);
            }
        });

        btnSubmitAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfile currentUser = CredentialToken.getInstance(ReviewAnswers.this).getUserProfile();
                int pointUpdate = currentUser.getPoint() + 1;
                currentUser.setPoint(pointUpdate);
                UpdateUserPointApi.getAPI(ReviewAnswers.this).update(new UpdateUserPointApi.API.UpdatePointRequest(pointUpdate)).enqueue(new retrofit2.Callback<UpdateUserPointApi.API.UpdatePointResponse>() {
                    @Override
                    public void onResponse(@NonNull retrofit2.Call<UpdateUserPointApi.API.UpdatePointResponse> call, @NonNull retrofit2.Response<UpdateUserPointApi.API.UpdatePointResponse> response) {
                        if (response.isSuccessful()) {
                            UpdateUserPointApi.API.UpdatePointResponse result = response.body();
                            assert result != null;
                            CredentialToken.getInstance(ReviewAnswers.this).setUserProfile(result.getUser());
                            Toast.makeText(ReviewAnswers.this, "Update success", Toast.LENGTH_SHORT).show();
                            loadingDialog.dismiss();
                            Intent intent = new Intent(ReviewAnswers.this, QuizDetailActivity.class);
                            intent.putExtra("quizId", quizId);
                            startActivity(intent);
                        } else {
                            Gson gson = new GsonBuilder().create();
                            assert response.errorBody() != null;
                            ErrorResponse error = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                            loadingDialog.showError(error.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateUserPointApi.API.UpdatePointResponse> call, Throwable t) {
                        Toast.makeText(ReviewAnswers.this, "Update failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public LinearLayout rowLinearQuestion(Question question, ArrayList<Answer> userAnswer) {
        LinearLayout questionSummaryLinearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
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

        // Tạo TextView cho câu hỏi
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

        // Thêm Button và TextView vào FrameLayout
        frameLayout.addView(btnOrder);
        frameLayout.addView(txtQuestion);

        // Thêm FrameLayout vào LinearLayout
        questionSummaryLinearLayout.addView(frameLayout);

        // Tạo các TextView và ImageView cho các câu trả lời
        for (Answer answerRe : userAnswer) {
            LinearLayout answerLayout = new LinearLayout(this);
            LinearLayout.LayoutParams answerLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            answerLayout.setLayoutParams(answerLayoutParams);
            answerLayout.setOrientation(LinearLayout.HORIZONTAL);
            answerLayout.setPadding(160, 10, 0, 10);

            // Tạo TextView cho đáp án
            TextView txtAnswer = new TextView(this);
            LinearLayout.LayoutParams txtAnswerLayoutParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
            );
            txtAnswer.setLayoutParams(txtAnswerLayoutParams);
            txtAnswer.setText(answerRe.getBody().isEmpty() ? "-" : "- Answer: " + answerRe.getBody());
            txtAnswer.setTextSize(16);
            txtAnswer.setTextColor(getResources().getColor(R.color.black));

            // Tạo ImageView cho trạng thái đúng/sai của đáp án
            ImageView imgResult = new ImageView(this);
            LinearLayout.LayoutParams imgLayoutParams = new LinearLayout.LayoutParams(
                    70,
                    70
            );
            imgResult.setLayoutParams(imgLayoutParams);
            imgResult.setImageResource(answerRe.isCorrect() ? R.drawable.ic_true_answer : R.drawable.error_answer_ic);

            // Thêm TextView và ImageView vào answerLayout
            answerLayout.addView(txtAnswer);
            answerLayout.addView(imgResult);

            // Thêm answerLayout vào questionSummaryLinearLayout
            questionSummaryLinearLayout.addView(answerLayout);
        }

        return questionSummaryLinearLayout;
    }
}
