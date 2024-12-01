package com.avichai98.preferencenews.Response;

import androidx.annotation.NonNull;

import com.avichai98.preferencenews.Utilities.User;

public class UserResponse {
    private String message;
    private User user;

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    @NonNull
    @Override
    public String toString() {
        return "UserResponse{" +
                "message='" + message + '\'' +
                ", user=" + user +
                '}';
    }
}