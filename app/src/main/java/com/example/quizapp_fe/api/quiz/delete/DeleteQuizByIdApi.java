package com.example.quizapp_fe.api.quiz.delete;

import android.content.Context;

import com.example.quizapp_fe.api.ApiClient;
import com.example.quizapp_fe.constants.ApiEndpoint;
import com.example.quizapp_fe.entities.Quiz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public class DeleteQuizByIdApi {
    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    public interface DeleteQuizApi {
        @DELETE(ApiEndpoint.QuizApiEndpoint.DELETE_QUIZ)
        Call<Quiz> DeleteQuizAPI(@Path("id") String id);
    }
    public static  DeleteQuizApi getAPI(Context context) {
        return new Retrofit.Builder()
                .baseUrl(ApiEndpoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(ApiClient.getInstance(context).getClient())
                .build()
                .create(DeleteQuizApi.class);
    }
}
