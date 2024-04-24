package com.example.quizapp_fe.constants;

public class ApiEndpoint {
    public static final String BASE_URL = "http://10.0.2.2:4000";

    public static class AuthApiEndpoint {
        public static final String AUTH = "/api/auth";
        public static final String LOGIN = AUTH + "/login";
        public static final String REGISTER = AUTH + "/register";
        public static final String GET_ME = AUTH + "/me";
    }

    public static class QuizApiEndpoint {
        public static final String QUIZ = "/api/quiz";
        public static final String GET_QUIZZES_PUBLIC = QUIZ + "/public";
        public static final String GET_QUIZZES_BY_SEARCH = QUIZ + "/search";
        public static final String GET_QUIZZES_DISCOVER_PAGE = QUIZ + "/discover";
        public static final String GET_QUIZ_BY_ID = QUIZ + "/{id}";
        public static final String GET_DRAFT_QUIZ_BY_ID = QUIZ + "/draft/{id}";
        public static final String GET_TEACHER_QUIZZES = QUIZ + "/teacher/{teacherId}";
        public static final String CREATE_QUIZ = QUIZ + "/";
        public static final String CREATE_DRAFT_QUIZ = QUIZ + "/draft";
        public static final String IMPORT_QUIZ = QUIZ + "/import";
        public static final String COMMENT_QUIZ = QUIZ + "/{id}/commentQuiz";
        public static final String UPDATE_QUIZ = QUIZ + "/{id}";
        public static final String LIKE_QUIZ = QUIZ + "/{id}/likeQuiz";
        public static final String DELETE_QUIZ = QUIZ + "/{id}";
    }

    public static class UserApiEndpoint {
        public static final String USER = "/api/user";
        public static final String GET_USER_BY_ID = USER + "/{id}";
        public static final String CHANGE_PASSWORD = USER + "/change-password";
        public static final String UPDATE_USER = USER + "/{id}";
        public static final String GET_LIST_RANKING = USER + "/ranking";
        public static final String ADD_FRIEND = USER + "/addFriend/{friendId}";
        public static final String UNFRIEND = USER + "/unfriend/{friendId}";
    }

    public static class GameApiEndpoint {
        public static final String GAME = "/api/game";
        public static final String GET_GAME_BY_ID = GAME + "/{id}";
        public static final String CREATE_GAME = GAME + "/";
        public static final String UPDATE_GAME = GAME + "/{id}";
        public static final String DELETE_GAME = GAME + "/{id}";
        public static final String ADD_PLAYER = GAME + "/{gameId}/addPlayer";
        public static final String REMOVE_PLAYER = GAME + "/{gameId}/removePlayer";
        public static final String ADD_PLAYER_RESULT = GAME + "/{gameId}/addPlayerResult";
    }

    public static class LeaderBoardApiEndpoint {
        public static final String LEADER_BOARD = "/api/leaderBoard";
        public static final String GET_LEADER_BOARD_BY_ID = LEADER_BOARD + "/{id}";
        public static final String GET_HISTORY = LEADER_BOARD + "/history/{id}";
        public static final String CREATE_LEADER_BOARD = LEADER_BOARD + "/";
        public static final String DELETE_LEADER_BOARD = LEADER_BOARD + "/{id}";
        public static final String ADD_PLAYER_RESULT = LEADER_BOARD + "/{leaderBoardId}/addPlayerResult";
        public static final String UPDATE_CURRENT_LEADER_BOARD = LEADER_BOARD + "/{leaderBoardId}/currentLeaderBoard";
    }

    public static class PlayerResultApiEndpoint {
        public static final String PLAYER_RESULT = "/api/playerResult";
        public static final String GET_PLAYER_RESULTS = PLAYER_RESULT + "/";
        public static final String GET_PLAYER_RESULT = PLAYER_RESULT + "/{id}";
        public static final String GET_ANSWERS = PLAYER_RESULT + "/{playerResultId}/answers";
        public static final String GET_ANSWER = PLAYER_RESULT + "/{playerResultId}/answers/{answerId}";
        public static final String CREATE_PLAYER_RESULT = PLAYER_RESULT + "/";
        public static final String UPDATE_PLAYER_RESULT = PLAYER_RESULT + "/{id}";
        public static final String ADD_PLAYER_RESULT = PLAYER_RESULT + "/{playerId}/results/{gameId}";
        public static final String UPDATE_ANSWER = PLAYER_RESULT + "/{playerResultId}/answers/{answerId}";
        public static final String ADD_ANSWER = PLAYER_RESULT + "/{playerResultId}/answers";
        public static final String DELETE_PLAYER_RESULT = PLAYER_RESULT + "/{id}";
        public static final String DELETE_ANSWER = PLAYER_RESULT + "/{playerResultId}/answers/{answerId}";
    }

    public static class CommunityApiEndpoint {
        public static final String COMMUNITY = "/api/community";
        public static final String GET_COMMUNITIES = COMMUNITY + "/";
        public static final String GET_COMMUNITY = COMMUNITY + "/{id}";
        public static final String CREATE_COMMUNITY = COMMUNITY + "/";
        public static final String ADD_QUIZ_COMMUNITY = COMMUNITY + "/{id}/quiz/{quizId}";
        public static final String DELETE_QUIZ_COMMUNITY = COMMUNITY + "/{id}/deleteQuiz/{quizId}";
        public static final String ADD_MESSAGE_CHAT_BOX = COMMUNITY + "/addMessage/{id}";
        public static final String UPDATE_COMMUNITY = COMMUNITY + "/{id}";
        public static final String DELETE_COMMUNITY = COMMUNITY + "/{id}";
    }
}

