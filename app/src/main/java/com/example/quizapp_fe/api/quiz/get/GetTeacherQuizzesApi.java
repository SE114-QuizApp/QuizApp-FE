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

public class GetTeacherQuizzesApi {
    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public interface QuizApi {
        @GET(ApiEndpoint.QuizApiEndpoint.GET_TEACHER_QUIZZES)
        Call<ArrayList<Quiz>> getTeacherQuiz(@Path("teacherId") String teacherId);
    }
    public static QuizApi getTeacherQuizAPI(Context context) {
        return new Retrofit.Builder()
                .baseUrl(ApiEndpoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(ApiClient.getInstance(context).getClient())
                .build()
                .create(QuizApi.class);
    }
}
