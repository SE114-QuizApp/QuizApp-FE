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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.api.ErrorResponse;
import com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult;
import com.example.quizapp_fe.api.account.registration.SignUpApi;
import com.example.quizapp_fe.dialogs.LoadingDialog;
import com.example.quizapp_fe.models.CredentialToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    ImageView backArrow;
    LinearLayout googleSignUpLnLayout;
    LinearLayout facebookSignUpLnLayout;
    TextView contractStatement;
    TextView signUpButton;

    EditText userNameETxt;
    EditText firstNameETxt;
    EditText lastNameETxt;
    EditText emailAddressETxt;
    EditText passwordETxt;
    EditText confirmPasswordETxt;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loadingDialog = new LoadingDialog(SignUpActivity.this);
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
                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
//        Facebook Button
        facebookSignUpLnLayout = findViewById(R.id.signUpFacebookLinearLayout);
        facebookSignUpLnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_normal);
                facebookSignUpLnLayout.startAnimation(animation);
                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        userNameETxt = findViewById(R.id.signUpUserNameEditText);
        firstNameETxt = findViewById(R.id.signUpFistNameEditText);
        lastNameETxt = findViewById(R.id.signUpLastNameEditText);
        emailAddressETxt = findViewById(R.id.signUpEmailEditText);
        passwordETxt = findViewById(R.id.signUpPasswordEditText);
        confirmPasswordETxt = findViewById(R.id.signUpConfirmPasswordEditText);


//        Sign Up Button
        signUpButton = findViewById(R.id.signUpSignUpButtonTextView);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_normal);
                signUpButton.startAnimation(animation);

                if (!checkValidation()) {
                    return;
                }
                loadingDialog.showLoading("Wait for sign up process...");

//                Call SignUpApi
                SignUpApi.api.signUpWithPassword(new SignUpApi.SignUpForm(userNameETxt.getText().toString(), firstNameETxt.getText().toString(), lastNameETxt.getText().toString(), emailAddressETxt.getText().toString(), passwordETxt.getText().toString())).enqueue(new Callback<LoginWithPasswordApiResult>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginWithPasswordApiResult> call, @NonNull Response<LoginWithPasswordApiResult> response) {
                        if (response.isSuccessful()) {
                            LoginWithPasswordApiResult result = response.body();

                            assert result != null;
                            CredentialToken.getInstance(SignUpActivity.this).setCredential(result.getUser().getId(), result.getAccessToken(), result.getRefreshToken(), result.getUser());

                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();

                            loadingDialog.dismiss();
                        } else {
                            Gson gson = new GsonBuilder().create();
                            assert response.errorBody() != null;
                            ErrorResponse error = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                            loadingDialog.showError(error.getMessage());
//                            Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginWithPasswordApiResult> call, @NonNull Throwable t) {
                        System.out.println(t.getMessage());
                        loadingDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, "Call API failure", Toast.LENGTH_SHORT).show();
                    }
                });

//                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
//                startActivity(intent);
//                finish();
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

    Boolean checkValidation() {
        if (userNameETxt.getText().toString().isEmpty()) {
            userNameETxt.setError("Username is required");
            return false;
        }
        if (firstNameETxt.getText().toString().isEmpty()) {
            firstNameETxt.setError("First name is required");
            return false;
        }
        if (lastNameETxt.getText().toString().isEmpty()) {
            lastNameETxt.setError("Last name is required");
            return false;
        }
        if (emailAddressETxt.getText().toString().isEmpty()) {
            emailAddressETxt.setError("Email is required");
            return false;
        }
        if (passwordETxt.getText().toString().isEmpty()) {
            passwordETxt.setError("Password is required");
            return false;
        }
        if (confirmPasswordETxt.getText().toString().isEmpty()) {
            confirmPasswordETxt.setError("Confirm password is required");
            return false;
        }
        if (!passwordETxt.getText().toString().equals(confirmPasswordETxt.getText().toString())) {
            confirmPasswordETxt.setError("Password and Confirm password must be the same");
            return false;
        }
        return true;
    }
}