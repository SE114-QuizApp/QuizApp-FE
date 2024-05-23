package com.example.quizapp_fe.api.quiz.create;

import android.content.Context;

import com.example.quizapp_fe.api.ApiClient;
import com.example.quizapp_fe.constants.ApiEndpoint;
import com.example.quizapp_fe.entities.Category;
import com.example.quizapp_fe.entities.Grade;
import com.example.quizapp_fe.entities.Question;
import com.example.quizapp_fe.entities.Quiz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class CreateQuizApi {
    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public interface API {
        class CreateQuizRequest {
            String backgroundImage;
            Category category;
            String description;
            Grade grade;
            boolean isDraft;
            boolean isPublic;
            String name;
            int numberOfQuestions;
            int pointsPerQuestion;
            ArrayList<Question> questionList;
            ArrayList<String> tags;

            public CreateQuizRequest(Quiz quiz) {
                this.backgroundImage = quiz.getBackgroundImage();
                this.category = quiz.getCategory();
                this.description = quiz.getDescription();
                this.grade = quiz.getGrade();
                this.isDraft = quiz.isDraft();
                this.isPublic = quiz.isPublic();
                this.name = quiz.getName();
                this.numberOfQuestions = quiz.getNumberOfQuestions();
                this.pointsPerQuestion = quiz.getPointsPerQuestion();
                this.questionList = quiz.getQuestionList();
                this.tags = quiz.getTags();
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