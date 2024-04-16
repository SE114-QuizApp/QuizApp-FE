package com.example.quizapp_fe.api.account.registration;

import com.example.quizapp_fe.api.account.auth.AuthenticationApi;
import com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult;
import com.example.quizapp_fe.constants.ApiEndpoint;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpApi {

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    SignUpApi api =
            new Retrofit.Builder().baseUrl(ApiEndpoint.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(SignUpApi.class);

    class SignUpForm {
        public String userName;
        public String firstName;
        public String lastName;
        public String mail;
        public String password;

        public SignUpForm(String userName, String firstName, String lastName, String mail, String password) {
            this.userName = userName;
            this.firstName = firstName;
            this.lastName = lastName;
            this.mail = mail;
            this.password = password;
        }

    }

    @POST(ApiEndpoint.AuthApiEndpoint.REGISTER)
    Call<LoginWithPasswordApiResult> signUpWithPassword(@Body SignUpForm signUpForm);
}
