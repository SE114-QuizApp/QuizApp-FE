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

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import com.bumptech.glide.Glide;
import com.example.quizapp_fe.R;
import com.example.quizapp_fe.api.quiz.get.GetQuizByIdApi;
import com.example.quizapp_fe.entities.Answer;
import com.example.quizapp_fe.entities.Question;
import com.example.quizapp_fe.entities.Quiz;
import com.example.quizapp_fe.models.JsonHelper;
import com.example.quizapp_fe.models.UserAnswers;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayQuiz extends AppCompatActivity {
    private TextView txtCountDown;
    private TextView txtContentQuestion;

    private TextView txtIndexOfCurrentQuestion;
    private TextView txtContentQuestionNoImage;
    private TextView txtPointAndChoice;
    private TextView txtPointAndChoiceNoImage;
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

    private LinearLayout lnStatusAnswerA;
    private LinearLayout lnStatusAnswerB;
    private LinearLayout lnStatusAnswerC;
    private LinearLayout lnStatusAnswerD;
    private LinearLayout lnAnswerGroup;


    private Quiz quiz;
    private String quizId;
    private ArrayList<Question> questionList;

    private int currentIndex = 0;


    private Question currentQuestion;


    private ArrayList<Answer> answersList;
    private int totalPoints;
    private int numberOfAnswerSelection = 0;

    // Review Answer
    private ArrayList<Answer> userAnswersPerQuestion;
    private Question userQuestion;

    private ArrayList<Question> userQuestionAnswer;

    private boolean isCheckBoxListenerEnabled = true;

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
        txtPointAndChoice = findViewById(R.id.pointAndChoice);
        txtPointAndChoiceNoImage = findViewById(R.id.pointAndChoiceNoImage);
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

        lnStatusAnswerA = findViewById(R.id.statusLinearA);
        lnStatusAnswerB = findViewById(R.id.statusLinearB);
        lnStatusAnswerC = findViewById(R.id.statusLinearC);
        lnStatusAnswerD = findViewById(R.id.statusLinearD);

        userQuestionAnswer = new ArrayList<Question>();

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        quizId = (String) intent.getSerializableExtra("quizId");
        Log.e("PlayQuiz", quizId);
//        quizId = "664c616eedf3c081af6829f6";
        // Gọi hàm renderQuizInformation và chờ đến khi hoàn tất để tiếp tục
        renderQuizInformation(quizId, () -> {
            questionList = quiz.getQuestionList();
            pgQuestionRemaining.setMax((quiz.getNumberOfQuestions() * 10));

            resetCbState();
            displayQuestion(currentIndex);

            changeStateCheckBox(lnAnswerA, cbAnswerA, 0);
            changeStateCheckBox(lnAnswerB, cbAnswerB, 2);
            changeStateCheckBox(lnAnswerC, cbAnswerC, 4);
            changeStateCheckBox(lnAnswerD, cbAnswerD, 6);

            // Bắt đầu đếm ngược từ 10 giây
            startCountdown(currentQuestion.getAnswerTime() + 1);
        });

        // Thêm click listener cho các LinearLayout chứa CheckBox
        // Thêm click listener cho các LinearLayout chứa CheckBox
        lnAnswerA.setOnClickListener(v -> handleLinearLayoutClick(lnAnswerA, cbAnswerA, 0));
        lnAnswerB.setOnClickListener(v -> handleLinearLayoutClick(lnAnswerB, cbAnswerB, 2));
        lnAnswerC.setOnClickListener(v -> handleLinearLayoutClick(lnAnswerC, cbAnswerC, 4));
        lnAnswerD.setOnClickListener(v -> handleLinearLayoutClick(lnAnswerD, cbAnswerD, 6));
    }

    public void renderQuizInformation(String inputQuizId, final Runnable callback) {
        GetQuizByIdApi.getAPI(PlayQuiz.this).getQuizById(inputQuizId).enqueue(new Callback<Quiz>() {
            @Override
            public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                Log.e("QuizDetail", "Call API Success By " + inputQuizId);
                if (response.isSuccessful()) {
                    quiz = response.body();
                    if (callback != null) {
                        callback.run();
                    }
                }
            }

            @Override
            public void onFailure(Call<Quiz> call, Throwable t) {
                Log.e("QuizDetail", "Call API Failure" + inputQuizId);
            }
        });
    }


    private void displayQuestion(int currentQuestionIndex) {
        // Lấy dữ liệu từng câu question và answer
        currentQuestion = questionList.get(currentQuestionIndex);
        answersList = currentQuestion.getAnswerList();

        numberOfAnswerSelection = 0;
        userQuestion = new Question();
        userAnswersPerQuestion = new ArrayList<Answer>();
        resetCbChecked();
        resetCbState();
        txtContentQuestionNoImage.setVisibility(View.GONE);
        txtPointAndChoiceNoImage.setVisibility(View.GONE);
        titleAndImageQuestion.setVisibility(View.VISIBLE);
        txtPointAndChoice.setVisibility(View.VISIBLE);
        // set init value
        userQuestion.setContent(currentQuestion.getContent());
        userQuestion.setQuestionIndex(currentIndex + 1);
        pgQuestionRemaining.setProgress((currentQuestionIndex + 1) * 10, true);
        btnPoint.setText(quiz.getPointsPerQuestion() + "");

        txtIndexOfCurrentQuestion.setText("QUESTION " + (currentQuestionIndex + 1) + " OF " + quiz.getNumberOfQuestions());
        txtContentQuestion.setText(currentQuestion.getContent());
        String text = "(" + quiz.getPointsPerQuestion() + " points - " + currentQuestion.getAnswerCorrect().size() + " choice)";
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtPointAndChoice.setText(spannableString);
        if (currentQuestion.getBackgroundImage().equals("")) {

            ViewGroup parent = (ViewGroup) titleAndImageQuestion.getParent();
            int index = parent.indexOfChild(titleAndImageQuestion);
            // Nếu không có Image, ẩn LinearLayout
            titleAndImageQuestion.setVisibility(View.GONE);

            // Cho hiện TextView không image
            txtContentQuestionNoImage.setVisibility(View.VISIBLE);
            txtContentQuestionNoImage.setText(currentQuestion.getContent());
            txtPointAndChoiceNoImage.setVisibility(View.VISIBLE);
            txtPointAndChoiceNoImage.setText("(" + quiz.getPointsPerQuestion() + " points - " + currentQuestion.getAnswerCorrect().size() + " choice)");
        } else {
            String imageUrl = currentQuestion.getBackgroundImage();
            Glide.with(this)
                 .load(imageUrl)
                 .placeholder(R.drawable.loading_image_background) // Hình ảnh hiển thị khi đang tải
                 .error(R.drawable.error_image_background) // Hình ảnh hiển thị khi có lỗi
                 .into(questionImage);
        }

        // Nếu số answer == 2 hoặc 4
        if (currentQuestion.getAnswerList().size() == 2 || currentQuestion.getQuestionType().equals("True/False")) {

            // Ẩn 2 đáp án sau
            lnAnswerC.setVisibility(View.GONE);
            lnAnswerD.setVisibility(View.GONE);

            // set answer
            cbAnswerA.setText(answersList.get(0).getBody());
            cbAnswerB.setText(answersList.get(1).getBody());
        } else if (currentQuestion.getAnswerList().size() == 4) {
            lnAnswerC.setVisibility(View.VISIBLE);
            lnAnswerD.setVisibility(View.VISIBLE);

            // set answer
            cbAnswerA.setText(answersList.get(0).getBody());
            cbAnswerB.setText(answersList.get(1).getBody());
            cbAnswerC.setText(answersList.get(2).getBody());
            cbAnswerD.setText(answersList.get(3).getBody());
        } else {
            // do nothing
        }
    }

    private void resetCbChecked() {
        cbAnswerA.setChecked(false);
        cbAnswerB.setChecked(false);
        cbAnswerC.setChecked(false);
        cbAnswerD.setChecked(false);

        lnStatusAnswerA.setEnabled(true);
        lnStatusAnswerB.setEnabled(true);
        lnStatusAnswerC.setEnabled(true);
        lnStatusAnswerD.setEnabled(true);

        lnStatusAnswerA.setVisibility(View.GONE);
        lnStatusAnswerB.setVisibility(View.GONE);
        lnStatusAnswerC.setVisibility(View.GONE);
        lnStatusAnswerD.setVisibility(View.GONE);

    }

    private void resetCbState() {
        for (int i = 1; i < lnAnswerGroup.getChildCount(); i += 2) {
            View childView = lnAnswerGroup.getChildAt(i);
            if (childView instanceof LinearLayout) {
                LinearLayout otherAnswerLayout = (LinearLayout) childView;
                otherAnswerLayout.setEnabled(true);
                Drawable initDrawable = ContextCompat.getDrawable(getBaseContext(), R.drawable.answer_rounded);
                otherAnswerLayout.setBackground(initDrawable);
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

                if (currentQuestion.getOptionQuestion().equals("Single")) {
                    // Kiểm tra xem người dùng đã chọn đáp án chưa
                    boolean isAnswerSelected = false;
                    if (cbAnswerA.isChecked() || cbAnswerB.isChecked() || cbAnswerC.isChecked() || cbAnswerD.isChecked()) {
                        isAnswerSelected = true;
                    }

                    // Nếu người dùng chưa chọn đáp án nào, thêm một answer rỗng
                    if (!isAnswerSelected) {
                        Answer emptyAnswer = new Answer("", "", false);
                        userAnswersPerQuestion.add(emptyAnswer);
                    }
                } else if (currentQuestion.getOptionQuestion().equals("Multiple")) {
                    // Nếu câu hỏi có nhiều đáp án mà user chưa trả lời đủ số câu trả lời quy định
                    if (numberOfAnswerSelection >= currentQuestion.getAnswerCorrect().size()) {
                        // do nothing
                    } else {
                        int answerUnselected = currentQuestion.getAnswerCorrect().size() - numberOfAnswerSelection;
                        if (answerUnselected > 0) {
                            for (int i = 0; i < answerUnselected; i++) {
                                boolean check = userAnswersPerQuestion.add(new Answer("", "", false));
                            }
                        }
                    }
                }
                userQuestion.setAnswerList(userAnswersPerQuestion);
                boolean check = userQuestionAnswer.add(userQuestion);

                if (currentIndex < quiz.getNumberOfQuestions() - 1) {

                    currentIndex += 1;
                    displayQuestion(currentIndex);
                    startCountdown(currentQuestion.getAnswerTime() + 1);
                } else {
                    // Nếu không còn câu hỏi nào navigate sang review answers
                    Intent intent = new Intent(PlayQuiz.this, ReviewAnswers.class);

                    UserAnswers model = new UserAnswers(userQuestionAnswer, totalPoints);
                    intent.putExtra("quizId", quizId);
                    intent.putExtra("userAnswers", model);
                    intent.putExtra("quizName", quiz.getName());
                    intent.putExtra("numberOfQuestions", quiz.getNumberOfQuestions());

                    startActivity(intent);
                }
            }

        }.start();
    }

    private void changeStateCheckBox(LinearLayout ln, CheckBox cb, int index) {
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isCheckBoxListenerEnabled) {

                    if (isChecked) {
                        Drawable roundedDrawable = ContextCompat.getDrawable(getBaseContext(), R.drawable.answer_rounded);
                        ln.setBackground(roundedDrawable);

                        // set color
                        int color = ContextCompat.getColor(getBaseContext(), R.color.purple_hue);
                        ColorStateList colorStateList = ColorStateList.valueOf(color);
                        CompoundButtonCompat.setButtonTintList(cb, colorStateList);

                        // Kiểm tra đáp án
                        checkAnswer(ln, cb, index);

                        if (currentQuestion.getOptionQuestion().equals("Single")) {
                            // Tắt các CheckBox khác
                            disableAllCheckBoxes();
                        } else {
                            if (numberOfAnswerSelection >= currentQuestion.getAnswerCorrect().size()) {
                                disableAllCheckBoxes();
                            } else {
                                cb.setEnabled(false);
                            }
                        }
                    } else {
                        Drawable roundedFalseDrawable = ContextCompat.getDrawable(getBaseContext(), R.drawable.answer_rounded_false);
                        ln.setBackground(roundedFalseDrawable);
                        // set color
                        int color = ContextCompat.getColor(getBaseContext(), R.color.dull_lavender);
                        ColorStateList colorStateList = ColorStateList.valueOf(color);
                        CompoundButtonCompat.setButtonTintList(cb, colorStateList);
                    }
                }

            }
        });
    }

    private void handleLinearLayoutClick(LinearLayout ln, CheckBox cb, int index) {
        if (!cb.isEnabled()) return;
        isCheckBoxListenerEnabled = false;
        cb.setChecked(!cb.isChecked());
        isCheckBoxListenerEnabled = true;
        if (cb.isChecked()) {
            Drawable roundedDrawable = ContextCompat.getDrawable(getBaseContext(), R.drawable.answer_rounded);
            ln.setBackground(roundedDrawable);

            // set color
            int color = ContextCompat.getColor(getBaseContext(), R.color.purple_hue);
            ColorStateList colorStateList = ColorStateList.valueOf(color);
            CompoundButtonCompat.setButtonTintList(cb, colorStateList);

            // Kiểm tra đáp án
            checkAnswer(ln, cb, index);

            if (currentQuestion.getOptionQuestion().equals("Single")) {
                // Tắt các CheckBox khác
                disableAllCheckBoxes();
            } else {
                if (numberOfAnswerSelection >= currentQuestion.getAnswerCorrect().size()) {
                    disableAllCheckBoxes();
                }
                else {
                    ln.setEnabled(false);
                    cb.setEnabled(false);
                }
            }
        } else {
            Drawable roundedFalseDrawable = ContextCompat.getDrawable(getBaseContext(), R.drawable.answer_rounded_false);
            ln.setBackground(roundedFalseDrawable);
            // set color
            int color = ContextCompat.getColor(getBaseContext(), R.color.dull_lavender);
            ColorStateList colorStateList = ColorStateList.valueOf(color);
            CompoundButtonCompat.setButtonTintList(cb, colorStateList);
        }
    }

    private void checkAnswer(LinearLayout linearLayout, CheckBox checkBox, int index) {
        String selectedAnswer = checkBox.getText().toString();
        boolean isCorrect = false;

        for (Answer answer : answersList) {

            // Lấy câu trả lời của user đổ vào mảng
            if (answer.getBody().equals(selectedAnswer)) {
                if (answer.isCorrect()) isCorrect = true;
                boolean isAdded = userAnswersPerQuestion.add(answer);
                break;
            }
        }

        if (isCorrect) {
            if (currentQuestion.getOptionQuestion().equals("Single")) {
                totalPoints += quiz.getPointsPerQuestion();
            } else {
                totalPoints += (quiz.getPointsPerQuestion() / currentQuestion.getAnswerCorrect().size());
            }
            Drawable trueDrawable = ContextCompat.getDrawable(getBaseContext(), R.drawable.bg_answer_checked_true);
            linearLayout.setBackground(trueDrawable);
            int color = ContextCompat.getColor(getBaseContext(), R.color.green_lime);
            ColorStateList colorStateList = ColorStateList.valueOf(color);
            CompoundButtonCompat.setButtonTintList(checkBox, colorStateList);
        } else {
            totalPoints += 0;
            Drawable falseDrawable = ContextCompat.getDrawable(getBaseContext(), R.drawable.border_20dp_salmon_pink);
            linearLayout.setBackground(falseDrawable);
            int color = ContextCompat.getColor(getBaseContext(), R.color.pastel_pink);
            ColorStateList colorStateList = ColorStateList.valueOf(color);
            CompoundButtonCompat.setButtonTintList(checkBox, colorStateList);
        }
        statusAnswerInfo(isCorrect, index);

        // Hiển thị đáp án đúng
        if (currentQuestion.getOptionQuestion().equals("Single")) {
            showCorrectAnswer();
        } else if (currentQuestion.getOptionQuestion().equals("Multiple")) {
            numberOfAnswerSelection += 1;
            if (numberOfAnswerSelection >= currentQuestion.getAnswerCorrect().size()) {
                showCorrectAnswer();
            }
        }
    }

    private void statusAnswerInfo(boolean isCorrect, int index) {
        View childView = lnAnswerGroup.getChildAt(index);

        if (childView instanceof LinearLayout) {
            LinearLayout otherAnswerLayout = (LinearLayout) childView;
            View childImageView = otherAnswerLayout.getChildAt(0);
            View childTextView = otherAnswerLayout.getChildAt(1);

            if (childImageView instanceof ImageView && childTextView instanceof TextView) {
                ImageView imgView = (ImageView) childImageView;
                TextView textView = (TextView) childTextView;

                otherAnswerLayout.setVisibility(View.VISIBLE);

                if (isCorrect) {
                    textView.setText("Congrat, Your answer is correct !");
                    Drawable correctIcon = ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_true_answer);
                    imgView.setImageDrawable(correctIcon);

                    int colorSucA = ContextCompat.getColor(getBaseContext(), R.color.green_lime);
                    textView.setTextColor(colorSucA);
                } else {
                    textView.setText("Unfortunately, your answer is wrong !");
                    int colorSuc = ContextCompat.getColor(getBaseContext(), R.color.red_real);
                    textView.setTextColor(colorSuc);

                    Drawable correctIcon = ContextCompat.getDrawable(getBaseContext(), R.drawable.error_answer_ic);
                    imgView.setImageDrawable(correctIcon);
                }
            } else {
                // Xử lý trường hợp không đúng loại của các phần tử con
                Log.e("statusAnswerInfo", "Expected ImageView and TextView but found different types.");
            }
        } else {
            // Xử lý trường hợp không đúng loại của phần tử con
            Log.e("statusAnswerInfo", "Expected LinearLayout but found different type.");
        }
    }


    private void showCorrectAnswer() {
        for (Answer answer : answersList) {
            if (answer.isCorrect()) {
                switch (answer.getName()) {
                    case "a":
                        Drawable trueDrawableA = ContextCompat.getDrawable(getBaseContext(), R.drawable.bg_answer_checked_true);
                        lnAnswerA.setBackground(trueDrawableA);
                        int colorA = ContextCompat.getColor(getBaseContext(), R.color.green_lime);
                        ColorStateList colorStateListA = ColorStateList.valueOf(colorA);
                        CompoundButtonCompat.setButtonTintList(cbAnswerA, colorStateListA);
                        break;
                    case "b":
                        Drawable trueDrawableB = ContextCompat.getDrawable(getBaseContext(), R.drawable.bg_answer_checked_true);
                        lnAnswerB.setBackground(trueDrawableB);
                        int colorB = ContextCompat.getColor(getBaseContext(), R.color.green_lime);
                        ColorStateList colorStateListB = ColorStateList.valueOf(colorB);
                        CompoundButtonCompat.setButtonTintList(cbAnswerB, colorStateListB);
                        break;
                    case "c":
                        Drawable trueDrawableC = ContextCompat.getDrawable(getBaseContext(), R.drawable.bg_answer_checked_true);
                        lnAnswerC.setBackground(trueDrawableC);
                        int colorC = ContextCompat.getColor(getBaseContext(), R.color.green_lime);
                        ColorStateList colorStateListC = ColorStateList.valueOf(colorC);
                        CompoundButtonCompat.setButtonTintList(cbAnswerC, colorStateListC);
                        break;
                    case "d":
                        Drawable trueDrawableD = ContextCompat.getDrawable(getBaseContext(), R.drawable.bg_answer_checked_true);
                        lnAnswerD.setBackground(trueDrawableD);
                        int colorD = ContextCompat.getColor(getBaseContext(), R.color.green_lime);
                        ColorStateList colorStateListD = ColorStateList.valueOf(colorD);
                        CompoundButtonCompat.setButtonTintList(cbAnswerD, colorStateListD);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void disableAllCheckBoxes() {
        for (int i = 1; i < lnAnswerGroup.getChildCount(); i += 2) {
            View childView = lnAnswerGroup.getChildAt(i);
            if (childView instanceof LinearLayout) {
                LinearLayout otherAnswerLayout = (LinearLayout) childView;
                otherAnswerLayout.setEnabled(false);
                CheckBox otherCheckBox = (CheckBox) otherAnswerLayout.getChildAt(0);
                otherCheckBox.setEnabled(false);
                int color = ContextCompat.getColor(getBaseContext(), R.color.mecha_metal);
                ColorStateList colorStateList = ColorStateList.valueOf(color);
                CompoundButtonCompat.setButtonTintList(otherCheckBox, colorStateList);
            }
        }
    }
}