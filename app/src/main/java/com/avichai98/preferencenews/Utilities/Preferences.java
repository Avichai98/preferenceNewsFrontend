package com.avichai98.preferencenews.Utilities;

import java.io.Serializable;
import java.util.ArrayList;

public class Preferences implements Serializable {
    private ArrayList<String> categories;
    private String language;
    private ArrayList<String> platforms;
    private String email;
    private long telegram_id;

    public Preferences() {
        categories = new ArrayList<>();
        platforms = new ArrayList<>();
    }

    public Preferences(ArrayList<String> categories, String language, ArrayList<String> platforms, String email, long telegram_id) {
        this.categories = categories;
        this.language = language;
        this.platforms = platforms;
        this.email = email;
        this.telegram_id = telegram_id;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ArrayList<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(ArrayList<String> platforms) {
        this.platforms = platforms;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getTelegramID() {
        return telegram_id;
    }

    public void setTelegramID(long telegram_id) {
        this.telegram_id = telegram_id;
    }

    @Override
    public String toString() {
        return '{' +
                "categories=" + categories +
                ", language='" + language + '\'' +
                ", platforms=" + platforms +
                ", email='" + email + '\'' +
                ", telegram_id='" + telegram_id + '\'' +
                '}';
    }
}
