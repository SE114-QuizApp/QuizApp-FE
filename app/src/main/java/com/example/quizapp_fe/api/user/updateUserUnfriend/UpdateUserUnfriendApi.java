package com.example.quizapp_fe.api.user.updateUserUnfriend;

import android.content.Context;

import com.example.quizapp_fe.api.ApiClient;
import com.example.quizapp_fe.api.user.updateUserFriend.UpdateUserFriendApi;
import com.example.quizapp_fe.constants.ApiEndpoint;
import com.example.quizapp_fe.entities.User;
import com.example.quizapp_fe.entities.UserProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class UpdateUserUnfriendApi {
    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public interface UserUnfriendApi {
        @PUT(ApiEndpoint.UserApiEndpoint.UNFRIEND)
        Call<UserProfile> updateUserUnfriend(@Path("friendId") String friendId);
    }

    public static UserUnfriendApi putAPI(Context context) {
        return new Retrofit.Builder()
                .baseUrl(ApiEndpoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(ApiClient.getInstance(context).getClient())
                .build()
                .create(UserUnfriendApi.class);
    }
}
