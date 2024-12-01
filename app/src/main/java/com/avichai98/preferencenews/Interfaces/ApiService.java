package com.avichai98.preferencenews.Interfaces;

import com.avichai98.preferencenews.Request.Authentication;
import com.avichai98.preferencenews.Response.UserResponse;
import com.avichai98.preferencenews.Utilities.Preferences;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @POST("/user/login")
    Call<UserResponse> login(@Body Authentication authentication);

    @POST("/user/register")
    Call<UserResponse> register(@Body Authentication authentication);


    @POST("/user/news")
    Call<String> requestNews(@Body Preferences preferences);

    @PUT("/user/preferences/{email}")
    Call<Map<String, String>> updatePreferences(@Path("email") String email, @Body Preferences preferences);

    @GET("/user/{email}")
    Call<UserResponse> getUserByEmail(@Path("email") String email);
}
