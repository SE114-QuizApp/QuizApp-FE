package com.example.quizapp_fe.api.account.auth;

import com.example.quizapp_fe.constants.ApiEndpoint;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationApi {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    AuthenticationApi api = new Retrofit.Builder().baseUrl(ApiEndpoint.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(AuthenticationApi.class);

    class Credential {
        public String mail;
        public String password;

        public Credential(String email, String password) {
            this.mail = email;
            this.password = password;
        }
    }

    @POST(ApiEndpoint.AuthApiEndpoint.LOGIN)
    Call<LoginWithPasswordApiResult> login(@Body Credential credential);

}
