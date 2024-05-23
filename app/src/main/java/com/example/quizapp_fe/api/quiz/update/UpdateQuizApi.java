package com.example.quizapp_fe.api.quiz.update;

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
import retrofit2.http.PUT;

public class UpdateQuizApi {
    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public interface API {
        class UpdateQuizRequest {
            Quiz quiz;

            public UpdateQuizRequest(Quiz quiz) {
                this.quiz = quiz;
            }
        }

        class UpdateQuizResponse {
            Quiz quiz;

            public UpdateQuizResponse(Quiz quiz) {
                this.quiz = quiz;
            }

            public Quiz getQuiz() {
                return quiz;
            }
        }

        @PUT(ApiEndpoint.QuizApiEndpoint.UPDATE_QUIZ)
        Call<UpdateQuizResponse> updateQuiz(@Body UpdateQuizRequest updateQuizRequest);
    }

    public static UpdateQuizApi.API getAPI(Context context) {
        return new Retrofit.Builder()
                .baseUrl(ApiEndpoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(ApiClient.getInstance(context).getClient())
                .build()
                .create(UpdateQuizApi.API.class);
    }
}