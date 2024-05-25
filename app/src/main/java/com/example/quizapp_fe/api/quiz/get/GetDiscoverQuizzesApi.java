package com.example.quizapp_fe.api.quiz.get;

import android.content.Context;

import com.example.quizapp_fe.api.ApiClient;
import com.example.quizapp_fe.constants.ApiEndpoint;
import com.example.quizapp_fe.entities.DiscoverQuiz;
import com.example.quizapp_fe.models.DiscoverQuizzes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class GetDiscoverQuizzesApi {

    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    // Sử dụng TypeToken để định nghĩa kiểu dữ liệu động
    static Type discoverQuizzesType = new TypeToken<Map<String, ArrayList<DiscoverQuiz>>>(){}.getType();

    public interface DiscoverApi {
        @GET(ApiEndpoint.QuizApiEndpoint.GET_QUIZZES_DISCOVER_PAGE)
        Call<Map<String, ArrayList<DiscoverQuiz>>> getDiscoverQuizzes();
    }

    public static DiscoverApi getAPI (Context context) {
        return new Retrofit.Builder()
                .baseUrl(ApiEndpoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(ApiClient.getInstance(context).getClient())
                .build()
                .create(DiscoverApi.class);
    }

    // Phương thức để lấy dữ liệu DiscoverQuizzes từ Map
    public static DiscoverQuizzes parseDiscoverQuizzes(Map<String, ArrayList<DiscoverQuiz>> map) {
        return new DiscoverQuizzes(map);
    }
}
