package com.example.quizapp_fe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.api.account.profile.GetMeApi;
import com.example.quizapp_fe.models.CredentialToken;
import com.example.quizapp_fe.models.MediaMangerObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        new MediaMangerObject().init(this);

        new Handler().postDelayed(() -> {
            if (!CredentialToken.getInstance(this).getUserId().isEmpty() && !CredentialToken.getInstance(this).getAccessToken().isEmpty()) {
                getMe();
            } else {
                Intent intent = new Intent(MainActivity.this, OnboardingActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }
    private void getMe() {
        GetMeApi.getAPI(this).getMe().enqueue(new retrofit2.Callback<com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult>() {
            @Override
            public void onResponse(retrofit2.Call<com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult> call, retrofit2.Response<com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;


                    System.out.println(response.body().getUser().getUsername());
                    System.out.printf("%s %s%n", response.body().getUser().getFirstName(), response.body().getUser().getLastName());
                    CredentialToken.getInstance(MainActivity.this).setUserProfile(response.body().getUser());
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Call API failure",
                                   Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, OnboardingActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(MainActivity.this, "Call API failure",
                               Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, OnboardingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}