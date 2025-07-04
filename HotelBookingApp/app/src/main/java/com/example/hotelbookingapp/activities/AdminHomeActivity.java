package com.example.hotelbookingapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hotelbookingapp.R;
import com.google.android.material.card.MaterialCardView;

public class AdminHomeActivity extends AppCompatActivity {
    private MaterialCardView btnManageRooms, btnManageBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        initializeViews();
        setupToolbar();
        setupClickListeners();
    }

    private void initializeViews() {
        btnManageRooms = findViewById(R.id.btnManageRooms);
        btnManageBookings = findViewById(R.id.btnManageBookings);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    private void setupClickListeners() {
        btnManageRooms.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageRoomsActivity.class));
        });

        btnManageBookings.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageBookingsActivity.class));
        });
    }
}
