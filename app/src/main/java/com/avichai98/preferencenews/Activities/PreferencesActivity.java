package com.avichai98.preferencenews.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.avichai98.preferencenews.Adapter.CategoryAdapter;
import com.avichai98.preferencenews.Adapter.PlatformAdapter;
import com.avichai98.preferencenews.Controllers.RetrofitClient;
import com.avichai98.preferencenews.Interfaces.ApiService;
import com.avichai98.preferencenews.R;
import com.avichai98.preferencenews.Response.UserResponse;
import com.avichai98.preferencenews.Utilities.CategoryManager;
import com.avichai98.preferencenews.Utilities.LanguageManager;
import com.avichai98.preferencenews.Utilities.PlatformManager;
import com.avichai98.preferencenews.Utilities.Preferences;
import com.avichai98.preferencenews.Utilities.User;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PreferencesActivity extends AppCompatActivity {
    private AutoCompleteTextView profile_ACTV_categories;
    private AutoCompleteTextView profile_ACTV_language;
    private AutoCompleteTextView profile_ACTV_platforms;
    private CategoryAdapter categoryAdapter;
    private PlatformAdapter platformAdapter;
    private EditText profile_EDT_email;  // This will be read-only
    private MaterialButton profile_BTN_bot;
    private MaterialButton profile_BTN_save;
    private MaterialButton profile_BTN_send_news;
    private User user;
    private CategoryManager categoryManager;
    private LanguageManager languageManager;
    private PlatformManager platformManager;
    ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefereneces);

        // Get the User object from the Intent
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("USER");  // Retrieve the User object
        categoryManager = new CategoryManager();
        languageManager = new LanguageManager();
        platformManager = new PlatformManager();
        findView();

        profile_BTN_bot.setOnClickListener(v -> botLink());
        profile_BTN_save.setOnClickListener(v -> saveChanges());
        profile_BTN_send_news.setOnClickListener(v -> sendNews());

        categoryAdapter = new CategoryAdapter(
                this,
                categoryManager.getCategories(),
                categoryManager.getCategoryState()
        );
        profile_ACTV_categories.setAdapter(categoryAdapter);

        // Find the AutoCompleteTextView
        profile_ACTV_language = findViewById(R.id.profile_ACTV_language);

        // Get the language names
        ArrayList<String> languageNames = new ArrayList<>(languageManager.getLanguages().values());

        // Create an ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, languageNames);

        // Set the adapter for the dropdown
        profile_ACTV_language.setAdapter(adapter);

        // Set default value (e.g., "English")
        if (user.getPreferences().getLanguage() == null || user.getPreferences().getLanguage()
                .isEmpty())
            profile_ACTV_language.setText("en", false);
        else
            profile_ACTV_language.setText(user.getPreferences().getLanguage(), false);

        platformAdapter = new PlatformAdapter(
                this,
                platformManager.getPlatforms(),
                platformManager.getPlatformState()
        );
        profile_ACTV_platforms.setAdapter(platformAdapter);
    }

    private void findView() {
        profile_ACTV_categories = findViewById(R.id.profile_ACTV_categories);
        profile_ACTV_language = findViewById(R.id.profile_ACTV_language);
        profile_ACTV_platforms = findViewById(R.id.profile_ACTV_platforms);
        profile_EDT_email = findViewById(R.id.profile_EDT_email);
        profile_BTN_bot = findViewById(R.id.profile_BTN_bot);
        profile_BTN_save = findViewById(R.id.profile_BTN_save);
        profile_BTN_send_news = findViewById(R.id.profile_BTN_send_news);

        profile_EDT_email.setText(user.getEmail());
        ArrayList<String> categories = user.getPreferences().getCategories();
        if (categories != null) {
            for (String category : categories) {
                categoryManager.getCategoryState().put(category, true);
            }
        }
        ArrayList<String> platforms = user.getPreferences().getPlatforms();
        if (platforms != null) {
            for (String platform : platforms) {
                platformManager.getPlatformState().put(platform, true);
            }
        }
    }

    private void botLink() {
        String userId = user.getId();
        Uri telegramUri = Uri.parse("https://t.me/Avichai98Bot?start=" + userId);
        Intent intent = new Intent(Intent.ACTION_VIEW, telegramUri);
        startActivity(intent);
    }

    private void sendNews() {
        Call<UserResponse> getCall = apiService.getUserByEmail(user.getEmail());
        getCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    assert userResponse != null;
                    user = userResponse.getUser();
                    Preferences preferences = new Preferences(user.getPreferences().getCategories(),
                            user.getPreferences().getLanguage(), user.getPreferences().getPlatforms(),
                            user.getEmail(), user.getPreferences().getTelegramID());

                    Log.d("Payload", new Gson().toJson(preferences));

                    Call<String> newsCall = apiService.requestNews(preferences);
                    newsCall.enqueue(new Callback<>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "News sent successfully!", Toast.LENGTH_SHORT).show();
                                Log.d("Response", new Gson().toJson(response.body()));
                            } else {
                                try {
                                    assert response.errorBody() != null;
                                    String error = response.errorBody().string();
                                    Toast.makeText(getApplicationContext(), "Failed to send news: " + error, Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Log.e("PreferencesActivity", "Failed to send news: " + e.getMessage());
                                }
                                Log.e("PreferencesActivity", "Failed to send news: " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("PreferencesActivity", "Error: " + t.getMessage());
                        }
                    });
                } else {
                    Log.e("PreferencesActivity", "Failed to get user: " + response.message());
                    Toast.makeText(getApplicationContext(), "Failed to get user: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("PreferencesActivity", "Error: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveChanges() {
        // Convert the selected categories (Map) to an ArrayList
        ArrayList<String> selectedCategories = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : categoryManager.getCategoryState().entrySet()) {
            if (entry.getValue()) {  // If the category is selected (true)
                selectedCategories.add(entry.getKey());  // Add the category to the list
            }
        }

        // Get the selected language
        String selectedLanguage = profile_ACTV_language.getText().toString().trim();

        // Validate the selected language
        if (TextUtils.isEmpty(selectedLanguage)) {
            selectedLanguage = "en"; // Default to English if no language is selected
        }

        // Set the language in user preferences
        user.getPreferences().setLanguage(selectedLanguage);

        // Convert the selected platforms (Map) to an ArrayList
        ArrayList<String> selectedPlatforms = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : platformManager.getPlatformState().entrySet()) {
            if (entry.getValue()) {  // If the platform is selected (true)
                selectedPlatforms.add(entry.getKey());  // Add the platform to the list
            }

        }
        // Save the selected categories and platforms (ArrayList) to the user preferences
        user.getPreferences().setCategories(selectedCategories);
        user.getPreferences().setPlatforms(selectedPlatforms);
        user.getPreferences().setEmail(user.getEmail());


        // Call the API to save preferences
        Preferences preferences = new Preferences(user.getPreferences().getCategories(),
                user.getPreferences().getLanguage(), user.getPreferences().getPlatforms(),
                user.getEmail(), user.getPreferences().getTelegramID());

        Log.d("Payload", new Gson().toJson(preferences));

        Call<Map<String, String>> call = apiService.updatePreferences(user.getEmail(), preferences);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Preferences saved successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        assert response.errorBody() != null;
                        String error = response.errorBody().string();
                        Toast.makeText(getApplicationContext(), "Failed to save preferences: " + error, Toast.LENGTH_SHORT).show();
                        Log.e("PreferencesActivity", "Failed to save preferences: " + error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, String>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("PreferencesActivity", "Error: " + t.getMessage());
            }
        });
    }
}
