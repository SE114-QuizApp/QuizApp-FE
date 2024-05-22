package com.example.quizapp_fe.api.user.updateUserPoint;

import android.content.Context;

import com.example.quizapp_fe.api.ApiClient;
import com.example.quizapp_fe.api.account.profile.UpdateProfileApi;
import com.example.quizapp_fe.constants.ApiEndpoint;
import com.example.quizapp_fe.entities.UserProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public class UpdateUserPointApi {
    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    public interface API {
        class UpdatePointRequest{
            int point;

            public UpdatePointRequest(int point) {
                this.point = point;
            }
        }

        class UpdatePointResponse{
            UserProfile user;

            public UpdatePointResponse(UserProfile user) {
                this.user = user;
            }

            public UserProfile getUser() {
                return user;
            }
        }
        @PUT(ApiEndpoint.UserApiEndpoint.UPDATE_POINTS)
        Call<UpdatePointResponse> update(@Body UpdatePointRequest updatePointRequest);
    }

    public static UpdateUserPointApi.API getAPI(Context context) {
        return new retrofit2.Retrofit.Builder().baseUrl(ApiEndpoint.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(ApiClient.getInstance(context).getClient()).build().create(UpdateUserPointApi.API.class);
    }
}
