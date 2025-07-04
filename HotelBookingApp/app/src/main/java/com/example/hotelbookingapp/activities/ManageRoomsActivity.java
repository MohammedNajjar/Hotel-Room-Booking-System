package com.example.hotelbookingapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.adapters.RoomAdminAdapter;
import com.example.hotelbookingapp.database.DBHelper;
import com.example.hotelbookingapp.models.Room;

import java.util.ArrayList;

public class ManageRoomsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnAddRoom;
    RoomAdminAdapter adapter;
    ArrayList<Room> roomList;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_rooms);

        recyclerView = findViewById(R.id.recyclerAdminRooms);
        btnAddRoom = findViewById(R.id.btnAddRoom);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DBHelper(this);
        loadRooms();

        btnAddRoom.setOnClickListener(v -> {
            showRoomDialog(null); // null = adding new
        });
    }

    private void loadRooms() {
        roomList = db.getAllRooms(); // returns all, even unavailable
        adapter = new RoomAdminAdapter(roomList, this, db);
        recyclerView.setAdapter(adapter);
    }

    private void showRoomDialog(@Nullable Room roomToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_edit_room, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText etRoomNumber = view.findViewById(R.id.etRoomNumber);
        EditText etRoomType = view.findViewById(R.id.etRoomType);
        EditText etRoomPrice = view.findViewById(R.id.etRoomPrice);
        CheckBox cbAvailable = view.findViewById(R.id.cbAvailable);
        Button btnPickImage = view.findViewById(R.id.btnPickImage);
        TextView tvImageFileName = view.findViewById(R.id.tvImageFileName);
        Button btnSave = view.findViewById(R.id.btnSaveRoom);
        Button btnCancel = view.findViewById(R.id.btnCancelRoom);

        if (roomToEdit != null) {
            etRoomNumber.setText(roomToEdit.getRoomNumber());
            etRoomType.setText(roomToEdit.getType());
            etRoomPrice.setText(String.valueOf(roomToEdit.getPrice()));
            cbAvailable.setChecked(roomToEdit.isAvailable());
            // يمكنك إضافة منطق لعرض اسم الصورة إذا أردت
        }

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            String roomNumber = etRoomNumber.getText().toString();
            String roomType = etRoomType.getText().toString();
            double price = Double.parseDouble(etRoomPrice.getText().toString());
            boolean available = cbAvailable.isChecked();
            String imageUri = ""; // placeholder
            String description = ""; // placeholder

            if (roomToEdit == null) {
                db.addRoom(roomNumber, roomType, price, description, imageUri, available);
                Toast.makeText(this, "Room added!", Toast.LENGTH_SHORT).show();
            } else {
                db.updateRoom(roomToEdit.getId(), roomNumber, roomType, price, description, imageUri, available);
                Toast.makeText(this, "Room updated!", Toast.LENGTH_SHORT).show();
            }
            loadRooms();
            dialog.dismiss();
        });

        dialog.show();
    }

    public void showEditDialog(Room room) {
        showRoomDialog(room);
    }
}
