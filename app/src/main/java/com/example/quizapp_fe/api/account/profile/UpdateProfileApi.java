package com.example.quizapp_fe.api.account.profile;

import android.content.Context;

import com.example.quizapp_fe.api.ApiClient;
import com.example.quizapp_fe.constants.ApiEndpoint;
import com.example.quizapp_fe.entities.UserProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public class UpdateProfileApi {
    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    public interface API {
        class EditProfileRequest{
            String firstName;
            String lastName;
            String userName;
            String avatar;
            String userType;

            public EditProfileRequest(String firstName, String lastName, String userName, String avatar, String userType) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.userName = userName;
                this.avatar = avatar;
                this.userType = userType;
            }
        }

        class EditProfileResponse{
            UserProfile user;

            public EditProfileResponse(UserProfile user) {
                this.user = user;
            }

            public UserProfile getUser() {
                return user;
            }
        }
        @PUT(ApiEndpoint.UserApiEndpoint.USER)
        Call<EditProfileResponse> update(@Body EditProfileRequest editProfileRequest);
    }

    public static UpdateProfileApi.API getAPI(Context context) {
        return new retrofit2.Retrofit.Builder().baseUrl(ApiEndpoint.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(ApiClient.getInstance(context).getClient()).build().create(UpdateProfileApi.API.class);
    }
}
