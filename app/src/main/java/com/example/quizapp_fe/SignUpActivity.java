package com.example.quizapp_fe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    ImageView backArrow;
    LinearLayout googleSignUpLnLayout;
    LinearLayout facebookSignUpLnLayout;
    TextView contractStatement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//        Back Arrow
        backArrow = findViewById(R.id.signUpBackArrowImgView);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_normal);
                backArrow.startAnimation(animation);
                Intent intent = new Intent(SignUpActivity.this, OnboardingActivity.class);
                startActivity(intent);
                finish();
            }
        });
//        Google Button
        googleSignUpLnLayout = findViewById(R.id.signUpGoogleLinearLayout);
        googleSignUpLnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_normal);
                googleSignUpLnLayout.startAnimation(animation);
            }
        });
//        Facebook Button
        facebookSignUpLnLayout = findViewById(R.id.signUpFacebookLinearLayout);
        facebookSignUpLnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_normal);
                facebookSignUpLnLayout.startAnimation(animation);
            }
        });
//        Contract Statement
        contractStatement = findViewById(R.id.signUpContractTextView);

        String nahStatement = getString(R.string.contract_statement);
        String termOfServices = getString(R.string.term_of_services);
        String privacyPolicy = getString(R.string.privacy_policy);

        String formattedStatement = String.format(nahStatement, termOfServices, privacyPolicy);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(formattedStatement);
        int termsOfServiceStart = formattedStatement.indexOf(termOfServices);
        int termsOfServiceEnd = termsOfServiceStart + termOfServices.length();
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), termsOfServiceStart, termsOfServiceEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        int privacyPolicyStart = formattedStatement.indexOf(privacyPolicy);
        int privacyPolicyEnd = privacyPolicyStart + privacyPolicy.length();
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), privacyPolicyStart, privacyPolicyEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        contractStatement.setText(spannableStringBuilder);
    }
}