package com.example.quizapp_fe.api.quiz.get;

import android.content.Context;

import com.example.quizapp_fe.api.ApiClient;
import com.example.quizapp_fe.constants.ApiEndpoint;
import com.example.quizapp_fe.entities.Quiz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class GetQuizByIdApi {
    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public interface QuizByIdApi {
        @GET(ApiEndpoint.QuizApiEndpoint.GET_QUIZ_BY_ID)
        Call<Quiz> getQuizById(@Path("id") String quizId);
    }

    public static QuizByIdApi getAPI(Context context) {
        return new Retrofit.Builder()
                .baseUrl(ApiEndpoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(ApiClient.getInstance(context).getClient())
                .build()
                .create(QuizByIdApi.class);

    }
}
