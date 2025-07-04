package com.example.hotelbookingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbookingapp.activities.AdminHomeActivity;
import com.example.hotelbookingapp.activities.LoginActivity;
import com.example.hotelbookingapp.activities.UserHomeActivity;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 1000; // 1 second

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Optional: You can set a splash screen layout here
        setContentView(R.layout.activity_main);

        // Fixed: Use Handler with explicit Looper to avoid deprecation warning
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
            String username = prefs.getString("username", null);

            if (username != null) {
                if (username.equals("ADMIN")) {
                    startActivity(new Intent(this, AdminHomeActivity.class));
                } else {
                    startActivity(new Intent(this, UserHomeActivity.class));
                }
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }

            finish();
        }, SPLASH_DELAY);
    }
}