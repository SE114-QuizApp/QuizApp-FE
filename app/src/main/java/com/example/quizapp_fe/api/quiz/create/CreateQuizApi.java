package com.example.quizapp_fe.api.quiz.create;

import android.content.Context;

import com.example.quizapp_fe.api.ApiClient;
import com.example.quizapp_fe.constants.ApiEndpoint;
import com.example.quizapp_fe.entities.Quiz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class CreateQuizApi {
    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public interface API {
        class CreateQuizRequest {
            Quiz quiz;

            public CreateQuizRequest(Quiz quiz) {
                this.quiz = quiz;
            }
        }

        class CreateQuizResponse {
            Quiz quiz;

            public CreateQuizResponse(Quiz quiz) {
                this.quiz = quiz;
            }

            public Quiz getQuiz() {
                return quiz;
            }
        }

        @POST(ApiEndpoint.QuizApiEndpoint.CREATE_QUIZ)
        Call<CreateQuizResponse> createQuiz(@Body CreateQuizRequest createQuizRequest);
    }

    public static CreateQuizApi.API getAPI(Context context) {
        return new Retrofit.Builder()
                .baseUrl(ApiEndpoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(ApiClient.getInstance(context).getClient())
                .build()
                .create(CreateQuizApi.API.class);
    }
}