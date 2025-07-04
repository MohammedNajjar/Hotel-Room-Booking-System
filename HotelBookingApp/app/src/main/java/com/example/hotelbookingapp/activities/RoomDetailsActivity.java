package com.example.hotelbookingapp.activities;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.database.DBHelper;
import com.example.hotelbookingapp.models.Room;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RoomDetailsActivity extends AppCompatActivity {

    private TextView tvRoomType, tvRoomPrice, tvRoomNumber, tvRoomDescription;
    private EditText etCheckIn, etCheckOut;
    private AutoCompleteTextView spinnerGuests;
    private MaterialButton btnBook;
    private DBHelper db;
    private int roomId;
    private Calendar checkInDate, checkOutDate;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        initializeViews();
        setupToolbar();
        setupDatePickers();
        setupGuestSpinner();
        loadRoomDetails();
        setupBookingButton();
    }

    private void initializeViews() {
        tvRoomType = findViewById(R.id.tvRoomType);
        tvRoomPrice = findViewById(R.id.tvRoomPrice);
        tvRoomNumber = findViewById(R.id.tvRoomNumber);
        tvRoomDescription = findViewById(R.id.tvRoomDescription);
        etCheckIn = findViewById(R.id.etCheckIn);
        etCheckOut = findViewById(R.id.etCheckOut);
        spinnerGuests = findViewById(R.id.spinnerGuests);
        btnBook = findViewById(R.id.btnBook);

        db = new DBHelper(this);
        roomId = getIntent().getIntExtra("room_id", -1);
        
        checkInDate = Calendar.getInstance();
        checkOutDate = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
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

    private void setupDatePickers() {
        etCheckIn.setOnClickListener(v -> showDatePicker(etCheckIn, true));
        etCheckOut.setOnClickListener(v -> showDatePicker(etCheckOut, false));
    }

    private void setupGuestSpinner() {
        String[] guestOptions = {"1 Guest", "2 Guests", "3 Guests", "4 Guests", "5 Guests"};
        ArrayAdapter<String> guestAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, guestOptions);
        spinnerGuests.setAdapter(guestAdapter);
        spinnerGuests.setText(guestOptions[0], false);
    }

    private void loadRoomDetails() {
        Room room = db.getRoomById(roomId);
        if (room != null) {
            tvRoomType.setText(room.getType());
            tvRoomPrice.setText("$" + String.format("%.0f", room.getPrice()) + "/night");
            tvRoomNumber.setText("Room #" + room.getRoomNumber());
            tvRoomDescription.setText(room.getDescription());
        }
    }

    private void setupBookingButton() {
        btnBook.setOnClickListener(v -> processBooking());
    }

    private void showDatePicker(EditText editText, boolean isCheckIn) {
        Calendar calendar = Calendar.getInstance();
        
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);
                    
                    if (isCheckIn) {
                        checkInDate = selectedDate;
                        etCheckIn.setText(dateFormat.format(selectedDate.getTime()));
                    } else {
                        checkOutDate = selectedDate;
                        etCheckOut.setText(dateFormat.format(selectedDate.getTime()));
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        
        // Set minimum date to today for check-in
        if (isCheckIn) {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        }
        
        datePickerDialog.show();
    }

    private void processBooking() {
        // Validate inputs
        if (etCheckIn.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please select check-in date", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (etCheckOut.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please select check-out date", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (spinnerGuests.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please select number of guests", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Validate dates
        if (checkOutDate.before(checkInDate) || checkOutDate.equals(checkInDate)) {
            Toast.makeText(this, "Check-out date must be after check-in date", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Get guest count
        String guestText = spinnerGuests.getText().toString();
        int guests = Integer.parseInt(guestText.split(" ")[0]);
        
        // Get user info
        SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
        String username = prefs.getString("username", null);
        int userId = db.getUserId(username);
        
        if (userId == -1) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Format dates for database
        String checkInStr = dateFormat.format(checkInDate.getTime());
        String checkOutStr = dateFormat.format(checkOutDate.getTime());
        
        // Add booking
        boolean inserted = db.addBooking(userId, roomId, checkInStr, checkOutStr, guests);
        if (inserted) {
            Toast.makeText(this, "Booking successful!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Booking failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
