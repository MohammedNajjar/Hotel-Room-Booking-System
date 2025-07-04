package com.example.hotelbookingapp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.adapters.BookingAdapter;
import com.example.hotelbookingapp.database.DBHelper;
import com.example.hotelbookingapp.models.Booking;

import java.util.ArrayList;

public class MyBookingsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookingAdapter adapter;
    private ArrayList<Booking> bookings;
    private DBHelper db;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        initializeViews();
        setupToolbar();
        loadBookings();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = new DBHelper(this);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void loadBookings() {
        SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
        String username = prefs.getString("username", null);
        userId = db.getUserId(username);

        bookings = db.getBookingsForUser(userId);
        adapter = new BookingAdapter(bookings, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh bookings when returning from other activities
        loadBookings();
    }
}
