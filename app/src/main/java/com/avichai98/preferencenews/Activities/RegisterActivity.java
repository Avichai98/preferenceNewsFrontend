package com.avichai98.preferencenews.Activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.avichai98.preferencenews.Controllers.RetrofitClient;
import com.avichai98.preferencenews.Interfaces.ApiService;
import com.avichai98.preferencenews.R;
import com.avichai98.preferencenews.Request.Authentication;
import com.avichai98.preferencenews.Response.UserResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText email_register, password_register;
    private MaterialButton register;
    private MaterialButton loginPage;
    ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findView();

        register.setOnClickListener(v -> registerAction());

        loginPage.setOnClickListener(v -> goLoginPage());
    }

    private void registerAction() {
        String email, password;
        email = String.valueOf(email_register.getText());
        password = String.valueOf(password_register.getText());

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
        }

        Call<UserResponse> call = apiService.register(new Authentication(email, password));

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    assert userResponse != null;
                    Toast.makeText(RegisterActivity.this, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    goLoginPage();
                } else {
                    // Handle unsuccessful response
                    try {
                        assert response.errorBody() != null;
                        Toast.makeText(RegisterActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        if (response.code() == 400)
                            goLoginPage();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // Handle failure
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.w(TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }


    private void goLoginPage() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void findView() {
        email_register = findViewById(R.id.register_TIE_email);
        password_register = findViewById(R.id.register_TIE_password);
        register = findViewById(R.id.register_BTN_register);
        loginPage = findViewById(R.id.register_BTN_loginPage);
    }
}