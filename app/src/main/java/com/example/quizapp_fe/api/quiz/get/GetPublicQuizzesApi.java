package com.example.quizapp_fe.api.quiz.get;

import android.content.Context;

import com.example.quizapp_fe.api.ApiClient;
import com.example.quizapp_fe.constants.ApiEndpoint;
import com.example.quizapp_fe.entities.PublicQuiz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class GetPublicQuizzesApi {
    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public interface PublicQuizzesApi {
        @GET(ApiEndpoint.QuizApiEndpoint.GET_QUIZZES_PUBLIC)
        Call<PublicQuiz> getPublicQuizzes(@Query("sectionName") String sectionName,
                                          @Query("page") String page,
                                          @Query("pageSize") String pageSize);
    }
    public static PublicQuizzesApi getAPI(Context context) {
        return new Retrofit.Builder()
                .baseUrl(ApiEndpoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(ApiClient.getInstance(context).getClient())
                .build()
                .create(PublicQuizzesApi.class);
    }
}
