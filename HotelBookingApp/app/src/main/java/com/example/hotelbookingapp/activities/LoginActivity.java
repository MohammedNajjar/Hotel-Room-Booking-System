package com.example.hotelbookingapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.database.DBHelper;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvRegister;
    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Auto-login
        SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
        String username = prefs.getString("username", null);
        if (username != null) {
            if (username.equals("ADMIN")) {
                startActivity(new Intent(this, AdminHomeActivity.class));
            } else {
                startActivity(new Intent(this, UserHomeActivity.class));
            }
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        db = new DBHelper(this);

        btnLogin.setOnClickListener(v -> {
            String user = etUsername.getText().toString();
            String pass = etPassword.getText().toString();

            if (user.equals("ADMIN") && pass.equals("ADMIN")) {
                SharedPreferences.Editor editor = getSharedPreferences("session", MODE_PRIVATE).edit();
                editor.putString("username", user);
                editor.apply();
                startActivity(new Intent(this, AdminHomeActivity.class));
            } else if (db.checkUser(user, pass)) {
                SharedPreferences.Editor editor = getSharedPreferences("session", MODE_PRIVATE).edit();
                editor.putString("username", user);
                editor.apply();
                startActivity(new Intent(this, UserHomeActivity.class));
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

}
