package com.example.hotelbookingapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.adapters.RoomAdapter;
import com.example.hotelbookingapp.database.DBHelper;
import com.example.hotelbookingapp.models.Room;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class UserHomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RoomAdapter adapter;
    private ArrayList<Room> roomList;
    private ArrayList<Room> filteredRoomList;
    private DBHelper db;
    private EditText etSearch;
    private TextView tvRoomCount;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        // Initialize views
        initializeViews();
        setupToolbar();
        setupBottomNavigation();
        setupSearch();
        loadRooms();

        Button btnMyBookings = findViewById(R.id.btnMyBookings);
        btnMyBookings.setOnClickListener(v -> {
            startActivity(new Intent(this, MyBookingsActivity.class));
        });
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerRooms);
        etSearch = findViewById(R.id.etSearch);
        tvRoomCount = findViewById(R.id.tvRoomCount);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = new DBHelper(this);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                // Already on home
                return true;
            } else if (itemId == R.id.nav_bookings) {
                startActivity(new Intent(this, MyBookingsActivity.class));
                return true;
            } else if (itemId == R.id.nav_logout) {
                logout();
                return true;
            }
            return false;
        });
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterRooms(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadRooms() {
        roomList = db.getAllAvailableRooms();
        filteredRoomList = new ArrayList<>(roomList);
        
        updateRoomCount();
        
        for (Room room : roomList) {
            Log.d("ROOM_DATA", room.getType() + " - $" + room.getPrice());
        }

        adapter = new RoomAdapter(filteredRoomList, this);
        recyclerView.setAdapter(adapter);
    }

    private void filterRooms(String query) {
        filteredRoomList.clear();
        
        if (query.isEmpty()) {
            filteredRoomList.addAll(roomList);
        } else {
            for (Room room : roomList) {
                if (room.getType().toLowerCase().contains(query.toLowerCase())) {
                    filteredRoomList.add(room);
                }
            }
        }
        
        updateRoomCount();
        adapter.notifyDataSetChanged();
    }

    private void updateRoomCount() {
        int count = filteredRoomList.size();
        tvRoomCount.setText(count + " room" + (count != 1 ? "s" : ""));
    }

    private void logout() {
        SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh rooms when returning from other activities
        loadRooms();
    }
}
