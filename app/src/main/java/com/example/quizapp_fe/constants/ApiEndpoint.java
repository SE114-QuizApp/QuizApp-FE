package com.example.quizapp_fe.constants;

public class ApiEndpoint {
    public static final String BASE_URL = "http://10.0.2.2:4000";

    public static class AuthApiEndpoint {
        public static final String AUTH = "/api/auth";
        public static final String LOGIN = AUTH + "/login";
        public static final String REGISTER = AUTH + "/register";
    }

    public static class QuizApiEndpoint {
        public static final String QUIZ = "/api/quiz";
        public static final String GET_QUIZZES = QUIZ + "/getQuizzes";
        public static final String GET_QUIZ = QUIZ + "/getQuiz";
        public static final String CREATE_QUIZ = QUIZ + "/createQuiz";
        public static final String UPDATE_QUIZ = QUIZ + "/updateQuiz";
        public static final String DELETE_QUIZ = QUIZ + "/deleteQuiz";
    }

}

