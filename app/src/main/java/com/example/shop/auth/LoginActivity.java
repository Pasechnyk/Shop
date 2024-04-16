package com.example.shop.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shop.MainActivity;
import com.example.shop.R;
import com.example.shop.services.ApplicationNetwork;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Вхід в додаток
public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilUsername, tilPassword;
    private TextInputEditText tieUsername, tiePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilUsername = findViewById(R.id.tilUsername);
        tilPassword = findViewById(R.id.tilPassword);
        tieUsername = findViewById(R.id.tieUsername);
        tiePassword = findViewById(R.id.tiePassword);
    }

    public void onLoginClicked(View view) {
        String username = tieUsername.getText().toString();
        String password = tiePassword.getText().toString();

        LoginRequest request = new LoginRequest(username, password);

        ApplicationNetwork.getInstance().getAuthService().login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Успішний вхід
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Handle failed login
                    Log.e("LoginActivity", "Login failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("LoginActivity", "Login failed: " + t.getMessage());
            }
        });
    }
}

