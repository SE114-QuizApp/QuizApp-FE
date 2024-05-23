package com.example.quizapp_fe.api.user.getUserById;

import android.content.Context;

import com.example.quizapp_fe.api.ApiClient;
import com.example.quizapp_fe.constants.ApiEndpoint;
import com.example.quizapp_fe.entities.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class GetUserByIdApi {
    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public interface UserByIdApi {
        @GET(ApiEndpoint.UserApiEndpoint.GET_USER_BY_ID)
        Call<User> getUserById(@Path("id") String id);
    }

    public static UserByIdApi getAPI(Context context) {
        return new Retrofit.Builder()
                .baseUrl(ApiEndpoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(ApiClient.getInstance(context).getClient())
                .build()
                .create(UserByIdApi.class);
    }
}
