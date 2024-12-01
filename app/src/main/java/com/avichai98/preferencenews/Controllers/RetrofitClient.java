package com.avichai98.preferencenews.Controllers;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.68.103:8000";
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            // Create OkHttpClient with custom timeouts
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();

            // Build Retrofit instance with the custom OkHttpClient
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)  // Set the custom OkHttpClient
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
