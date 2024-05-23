package com.example.quizapp_fe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quizapp_fe.R;
import com.example.quizapp_fe.adapters.QuestionDetailAdapter;
import com.example.quizapp_fe.api.quiz.get.GetQuizByIdApi;
import com.example.quizapp_fe.entities.Question;
import com.example.quizapp_fe.entities.Quiz;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizDetailActivity extends AppCompatActivity {

    private ImageView backArrowImageView;
    private TextView quizDetailQuizCategoryTextView;
    private TextView quizDetailQuizTitleTextView;
    private TextView quizDetailNumberOfQuestions;
    private TextView quizDetailTotalPointsTextView;
    private TextView quizDetailDescriptionTextView;
    private CircleImageView quizDetailCreatorAvatarImageView;
    private TextView quizDetailCreatorNameTextView;
    private AppCompatButton quizDetailPlaySoloButton;
    private RecyclerView quizDetailQuestionListRecyclerView;
    private ArrayList<Question> questionArrayList;
    private QuestionDetailAdapter questionDetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            quizDetailQuizCategoryTextView = findViewById(R.id.quizDetailQuizCategoryTextView);
            quizDetailQuizTitleTextView = findViewById(R.id.quizDetailQuizTitleTextView);
            quizDetailNumberOfQuestions = findViewById(R.id.quizDetailNumberOfQuestions);
            quizDetailTotalPointsTextView = findViewById(R.id.quizDetailTotalPointsTextView);
            quizDetailDescriptionTextView = findViewById(R.id.quizDetailDescriptionTextView);
            quizDetailCreatorAvatarImageView = findViewById(R.id.quizDetailCreatorAvatarImageView);
            quizDetailCreatorNameTextView = findViewById(R.id.quizDetailCreatorNameTextView);
            quizDetailPlaySoloButton = findViewById(R.id.quizDetailPlaySoloButton);
            quizDetailQuestionListRecyclerView = findViewById(R.id.quizDetailQuestionListRecyclerView);

            questionArrayList = new ArrayList<>();

            Bundle bundle = getIntent().getExtras();
            if(bundle != null) {
                String quizId = bundle.getString("quizId");
                Log.e("QuizDetail", quizId);
                renderQuizInformation(quizId);
            }

            backArrowImageView = findViewById(R.id.quizDetailBackArrowImageView);
            backArrowImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_normal);
                    backArrowImageView.startAnimation(animation);
                    Intent intent = new Intent( QuizDetailActivity.this, DiscoverySearchActivity.class);
                    startActivity(intent);
                }
            });

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void renderQuizInformation(String inputQuizId) {
        GetQuizByIdApi.getAPI(QuizDetailActivity.this).getQuizById(inputQuizId).enqueue(new Callback<Quiz>() {
            @Override
            public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                Log.e("QuizDetail", "Call API Success By " + inputQuizId);
                if(response.isSuccessful()) {
                    Quiz quiz = response.body();
                    quizDetailQuizCategoryTextView.setText(quiz.getCategory().getName());
                    quizDetailQuizTitleTextView.setText(quiz.getName());
                    quizDetailNumberOfQuestions.setText(quiz.getNumberOfQuestions() + " Questions");
                    quizDetailTotalPointsTextView.setText(quiz.getTotalPoints() + " Points");
                    quizDetailDescriptionTextView.setText(quiz.getDescription());

                    Glide.with(QuizDetailActivity.this)
                            .asBitmap()
                            .load(quiz.getCreator().getAvatar())
                            .error(R.drawable.img_office_man_512)
                            .into(quizDetailCreatorAvatarImageView);

                    quizDetailCreatorNameTextView.setText(quiz.getCreator().getFullName());

                    ArrayList<Question> questionsFromQuiz = new ArrayList<>(quiz.getQuestionList());

                    for (int i = 0; i < questionsFromQuiz.size(); i++) {
                        Question question = questionsFromQuiz.get(i);
                        questionArrayList.add(question);
                    }
                    questionDetailAdapter = new QuestionDetailAdapter(QuizDetailActivity.this, questionArrayList);
                    quizDetailQuestionListRecyclerView.setAdapter(questionDetailAdapter);
                    quizDetailQuestionListRecyclerView.setLayoutManager(new GridLayoutManager(QuizDetailActivity.this, 1));

                    quizDetailPlaySoloButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_normal);
                            quizDetailPlaySoloButton.startAnimation(animation);
                            Intent intent = new Intent(QuizDetailActivity.this, PlayQuiz.class);
                            intent.putExtra("quizId", quiz.get_id());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Quiz> call, Throwable t) {
                Log.e("QuizDetail", "Call API Failure" + inputQuizId);
            }
        });
    }
}

