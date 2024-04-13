package com.example.quizapp_fe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp_fe.R;

public class ResetPasswordActivity extends AppCompatActivity {
    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        backArrow = findViewById(R.id.resetPasswordBackArrowImageView);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_normal);
                backArrow.startAnimation(animation);
                Intent intent = new Intent(ResetPasswordActivity.this , SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}