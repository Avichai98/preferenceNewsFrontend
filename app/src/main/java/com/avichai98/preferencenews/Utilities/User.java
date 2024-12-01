package com.avichai98.preferencenews.Utilities;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;


public class User implements Serializable {
    private String id;
    private String email;
    private Preferences preferences;

    public User(String id, String email) {
        this.id = id;
        this.email = email;
        preferences = new Preferences();
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }


    public Preferences getPreferences() {
        return preferences;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", preferences=" + preferences +
                '}';
    }
}
