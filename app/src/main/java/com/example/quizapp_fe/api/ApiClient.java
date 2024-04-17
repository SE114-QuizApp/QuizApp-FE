package com.example.quizapp_fe.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.quizapp_fe.activities.OnboardingActivity;
import com.example.quizapp_fe.dialogs.LoadingDialog;
import com.example.quizapp_fe.models.CredentialToken;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiClient {
    static ApiClient apiClient;
    OkHttpClient client;

    public static ApiClient getInstance(Context context) {
        if (apiClient == null) {
            apiClient = new ApiClient(context);
        }
        return apiClient;
    }

    public ApiClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                String accessToken = CredentialToken.getInstance(context).getAccessToken();
                Request newRequest = chain.request().newBuilder()
                                          .addHeader("Authorization", "Bearer " + accessToken)
                                          .build();

                Response response = chain.proceed(newRequest);
                System.out.println(response.code());
                if (response.code() == 401) {
                    CredentialToken.getInstance(context).clearCredential();
                    Intent intent = new Intent(context, OnboardingActivity.class);
                    intent.putExtra("message", "Your session has expired. Please login again.");
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
                return response;
            }


        });
        client = builder.build();
    }

    public OkHttpClient getClient() {
        return client;
    }
}
