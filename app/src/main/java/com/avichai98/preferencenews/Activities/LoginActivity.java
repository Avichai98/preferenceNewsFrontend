package com.avichai98.preferencenews.Activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.avichai98.preferencenews.Controllers.RetrofitClient;
import com.avichai98.preferencenews.Interfaces.ApiService;
import com.avichai98.preferencenews.R;
import com.avichai98.preferencenews.Request.Authentication;
import com.avichai98.preferencenews.Response.UserResponse;
import com.avichai98.preferencenews.Utilities.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText email_login, password_login;
    private MaterialButton login;
    private MaterialButton registerPage;
    private User user;
    ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();

        login.setOnClickListener(v -> loginAction());

        registerPage.setOnClickListener(v -> goToRegisterPage());

    }

    private void goToRegisterPage() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void loginAction() {
        String email, password;
        email = String.valueOf(email_login.getText());
        password = String.valueOf(password_login.getText());

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
        }

        Call<UserResponse> call = apiService.login(new Authentication(email, password));

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    UserResponse userResponse = response.body();
                    assert userResponse != null;
                    Toast.makeText(LoginActivity.this, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.w(TAG, userResponse.getMessage());
                    user = new User(userResponse.getUser().getId(),userResponse.getUser().getEmail());
                    user.setPreferences(userResponse.getUser().getPreferences());
                    // Pass the user object to the PreferencesActivity
                    Intent intent = new Intent(getApplicationContext(), PreferencesActivity.class);
                    intent.putExtra("USER", user);  // Pass the User object as an extra
                    startActivity(intent);
                    finish();
                }
                else {
                    // Handle unsuccessful response
                    try {
                        assert response.errorBody() != null;
                        Toast.makeText(LoginActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Log.w(TAG, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                // Handle failure
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.w(TAG, Objects.requireNonNull(t.getMessage()));

            }
        });

    }

    private void findView() {
        email_login = findViewById(R.id.login_TIE_email);
        password_login = findViewById(R.id.login_TIE_password);
        login = findViewById(R.id.login_BTN_login);
        registerPage = findViewById(R.id.login_BTN_RegisterPage);
    }
}