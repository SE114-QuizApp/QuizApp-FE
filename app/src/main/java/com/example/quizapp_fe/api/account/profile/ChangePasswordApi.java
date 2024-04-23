package com.example.quizapp_fe.api.account.profile;

import android.content.Context;

import com.example.quizapp_fe.api.ApiClient;
import com.example.quizapp_fe.constants.ApiEndpoint;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public class ChangePasswordApi {

    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    public interface API {
        class ChangePasswordRequest{
            String oldPassword;
            String newPassword;

            public ChangePasswordRequest(String oldPassword, String newPassword) {
                this.oldPassword = oldPassword;
                this.newPassword = newPassword;
            }
        }

        class ChangePasswordResponse{
            String message;

            public ChangePasswordResponse(String message) {
                this.message = message;
            }

            public String getMessage() {
                return message;
            }
        }
        @PUT(ApiEndpoint.UserApiEndpoint.CHANGE_PASSWORD)
        Call<ChangePasswordResponse> changePassword(@Body ChangePasswordRequest changePasswordRequest);
    }

    public static ChangePasswordApi.API getAPI(Context context) {
        return new retrofit2.Retrofit.Builder().baseUrl(ApiEndpoint.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(ApiClient.getInstance(context).getClient()).build().create(ChangePasswordApi.API.class);
    }
}
