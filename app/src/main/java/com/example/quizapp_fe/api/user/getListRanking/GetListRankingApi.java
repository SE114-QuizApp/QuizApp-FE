package com.example.quizapp_fe.api.user.getListRanking;

import android.content.Context;

import com.example.quizapp_fe.api.ApiClient;
import com.example.quizapp_fe.constants.ApiEndpoint;
import com.example.quizapp_fe.entities.UserRank;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class GetListRankingApi {

    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public interface API {
        @GET(ApiEndpoint.UserApiEndpoint.GET_LIST_RANKING)
        Call<ArrayList<UserRank>> getListRanking();
    }

    public static API getAPI(Context context){
        return new retrofit2.Retrofit.Builder().baseUrl(ApiEndpoint.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(ApiClient.getInstance(context).getClient()).build().create(API.class);
    }
}
