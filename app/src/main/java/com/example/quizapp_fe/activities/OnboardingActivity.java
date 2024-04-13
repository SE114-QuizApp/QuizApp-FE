package com.example.quizapp_fe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.quizapp_fe.R;

public class OnboardingActivity extends AppCompatActivity {

    TextView logInTextView;
    TextView signUpTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
//      Login
        logInTextView = findViewById(R.id.onboardingLoginTextView);
        logInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnboardingActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
//      Sign up
        signUpTextView = findViewById(R.id.onboardingSignUpButtonTextView);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_normal);
                signUpTextView.startAnimation(animation);
                Intent intent = new Intent(OnboardingActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}