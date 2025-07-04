package com.example.hotelbookingapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.adapters.AdminBookingAdapter;
import com.example.hotelbookingapp.database.DBHelper;
import com.example.hotelbookingapp.models.Booking;

import java.util.ArrayList;

public class ManageBookingsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdminBookingAdapter adapter;
    ArrayList<Booking> bookings;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bookings);

        recyclerView = findViewById(R.id.recyclerManageBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DBHelper(this);
        bookings = db.getAllBookings(); // Admin can see all
        adapter = new AdminBookingAdapter(bookings, this, db);
        recyclerView.setAdapter(adapter);
    }
}
