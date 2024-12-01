package com.avichai98.preferencenews.Controllers;

import com.avichai98.preferencenews.Interfaces.ApiService;
import com.avichai98.preferencenews.Response.UserResponse;
import com.avichai98.preferencenews.Utilities.Preferences;
import com.avichai98.preferencenews.Utilities.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkHelper {

    private ApiService apiService;

    public NetworkHelper(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getUserData(String email, final UserDataCallback callback) {
        Call<UserResponse> getCall = apiService.getUserByEmail(email);
        getCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    assert userResponse != null;
                    callback.onUserDataFetched(userResponse.getUser());
                } else {
                    callback.onFailure("Failed to get user: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    public void requestNews(Preferences preferences, final NewsCallback callback) {
        Call<String> newsCall = apiService.requestNews(preferences);
        newsCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callback.onNewsSent(response.body());
                } else {
                    callback.onFailure("Failed to send news: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    // Callback interfaces for communication back to the Activity
    public interface UserDataCallback {
        void onUserDataFetched(User user);
        void onFailure(String errorMessage);
    }

    public interface NewsCallback {
        void onNewsSent(String news);
        void onFailure(String errorMessage);
    }
}

