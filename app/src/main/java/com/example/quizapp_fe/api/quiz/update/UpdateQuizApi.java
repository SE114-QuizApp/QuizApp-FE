package com.example.quizapp_fe.api.quiz.update;

import android.content.Context;

import com.example.quizapp_fe.api.ApiClient;
import com.example.quizapp_fe.constants.ApiEndpoint;
import com.example.quizapp_fe.entities.Category;
import com.example.quizapp_fe.entities.Creator;
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
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class UpdateQuizApi {
    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public interface API {
        class UpdateQuizRequest {
            String _id;
            String name;
            String description;
            String backgroundImage;
            int pointsPerQuestion;
            int numberOfQuestions;
            boolean isPublic;
            ArrayList<String> tags;
            ArrayList<String> likesCount;
            boolean isDraft;
            ArrayList<String> comments;
            ArrayList<Question> questionList;
            String createdAt;
            String updatedAt;
            Creator creator;
            Category category;
            Grade grade;

            public UpdateQuizRequest(Quiz quiz) {
                this._id = quiz.get_id();
                this.name = quiz.getName();
                this.description = quiz.getDescription();
                this.backgroundImage = quiz.getBackgroundImage();
                this.pointsPerQuestion = quiz.getPointsPerQuestion();
                this.numberOfQuestions = quiz.getNumberOfQuestions();
                this.isPublic = quiz.isPublic();
                this.tags = quiz.getTags();
                this.likesCount = quiz.getLikesCount();
                this.isDraft = quiz.isDraft();
                this.comments = quiz.getComments();
                this.questionList = quiz.getQuestionList();
                this.createdAt = quiz.getCreatedAt();
                this.updatedAt = quiz.getUpdatedAt();
                this.creator = quiz.getCreator();
                this.category = quiz.getCategory();
                this.grade = quiz.getGrade();
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
        Call<UpdateQuizResponse> updateQuiz(@Path("id") String id, @Body UpdateQuizRequest updateQuizRequest);
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