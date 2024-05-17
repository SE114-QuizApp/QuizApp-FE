package com.example.quizapp_fe.api.quiz.get;

import android.content.Context;

import com.example.quizapp_fe.api.ApiClient;
import com.example.quizapp_fe.constants.ApiEndpoint;
import com.example.quizapp_fe.models.DiscoverQuizzes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class GetDiscoverQuizzesApi {

    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public interface DiscoverApi {
        @GET(ApiEndpoint.QuizApiEndpoint.GET_QUIZZES_DISCOVER_PAGE)
        Call<DiscoverQuizzes> getDiscoverQuizzes();
    }

    public static DiscoverApi getAPI (Context context) {
        return new retrofit2.Retrofit.Builder()
                .baseUrl(ApiEndpoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(ApiClient.getInstance(context).getClient())
                .build()
                .create(DiscoverApi.class);
    }
}
