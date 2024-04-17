package com.example.quizapp_fe.api.account.profile;

import android.content.Context;

import com.example.quizapp_fe.api.ApiClient;
import com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult;
import com.example.quizapp_fe.constants.ApiEndpoint;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class GetMeApi {
    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public interface API {
        @GET(ApiEndpoint.AuthApiEndpoint.GET_ME)
        Call<LoginWithPasswordApiResult> getMe();
    }
    public static API getAPI(Context context){
        return new retrofit2.Retrofit.Builder().baseUrl(ApiEndpoint.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(ApiClient.getInstance(context).getClient()).build().create(API.class);
    }
}
