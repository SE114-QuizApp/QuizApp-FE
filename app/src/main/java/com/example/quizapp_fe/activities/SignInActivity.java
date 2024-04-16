package com.example.quizapp_fe.activities;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.api.ErrorResponse;
import com.example.quizapp_fe.api.account.auth.AuthenticationApi;
import com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult;
import com.example.quizapp_fe.dialogs.LoadingDialog;
import com.example.quizapp_fe.models.CredentialToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    ImageView backArrow;
    LinearLayout googleSignInLnLayout;
    LinearLayout facebookSignInLnLayout;
    TextView contractStatement;
    TextView signInButton;
    TextView forgotPasswordTextView;
    TextView singInEmailEditText;
    TextView signInPasswordEditText;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        loadingDialog = new LoadingDialog(SignInActivity.this);
//        Back Arrow
        backArrow = findViewById(R.id.signInBackArrowImgView);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_normal);
                backArrow.startAnimation(animation);
                Intent intent = new Intent(SignInActivity.this, OnboardingActivity.class);
                startActivity(intent);
                finish();
            }
        });
//        Google button
        googleSignInLnLayout = findViewById(R.id.signInGoogleLinearLayout);
        googleSignInLnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_normal);
                googleSignInLnLayout.startAnimation(animation);
            }
        });
//        Facebook button
        facebookSignInLnLayout = findViewById(R.id.signInFacebookLinearLayout);
        facebookSignInLnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_normal);
                facebookSignInLnLayout.startAnimation(animation);
            }
        });
//        Sign In Button
        singInEmailEditText = findViewById(R.id.signInEmailEditText);
        signInPasswordEditText = findViewById(R.id.signInPasswordEditText);

        signInButton = findViewById(R.id.signInSignInButtonTextView);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_normal);
                signInButton.startAnimation(animation);

                if (!checkValidation()) {
                    return;
                }
                loadingDialog.showLoading("Signing in...");

                AuthenticationApi.api.login(new AuthenticationApi.Credential(singInEmailEditText.getText().toString(), signInPasswordEditText.getText().toString())).enqueue(new Callback<LoginWithPasswordApiResult>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginWithPasswordApiResult> call, @NonNull Response<LoginWithPasswordApiResult> response) {
                        if (response.isSuccessful()) {
                            LoginWithPasswordApiResult result = response.body();

                            assert result != null;
                            CredentialToken.getInstance(SignInActivity.this).setCredential(result.getUser().getId(), result.getAccessToken(), result.getRefreshToken(), result.getUser());

                            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                            loadingDialog.dismiss();

                        }else {
                            Gson gson = new GsonBuilder().create();
                            assert response.errorBody() != null;
                            ErrorResponse error = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                            loadingDialog.showError(error.getMessage());
//                            Toast.makeText(SignInActivity.this, error.getMessage(),
//                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginWithPasswordApiResult> call, @NonNull Throwable t) {
                        System.out.println(t.getMessage());
                        Toast.makeText(SignInActivity.this, "Call API failure",
                                Toast.LENGTH_SHORT).show();
                    }
                });


//
            }
        });
//        Forgot Password
        forgotPasswordTextView = findViewById(R.id.signInForgotPasswordTextView);
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_normal);
                forgotPasswordTextView.startAnimation(animation);
                Intent intent = new Intent(SignInActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
//        Contract Statement
        contractStatement = findViewById(R.id.signInContractTextView);

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

    Boolean checkValidation() {
        if (singInEmailEditText.getText().toString().isEmpty()) {
            singInEmailEditText.setError("Email is required");
            return false;
        }
        if (signInPasswordEditText.getText().toString().isEmpty()) {
            signInPasswordEditText.setError("Password is required");
            return false;
        }
        return true;
    }
}