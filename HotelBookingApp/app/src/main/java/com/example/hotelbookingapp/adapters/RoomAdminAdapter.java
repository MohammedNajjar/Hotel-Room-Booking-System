package com.example.hotelbookingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.activities.ManageRoomsActivity;
import com.example.hotelbookingapp.database.DBHelper;
import com.example.hotelbookingapp.models.Room;

import java.util.ArrayList;

public class RoomAdminAdapter extends RecyclerView.Adapter<RoomAdminAdapter.RoomViewHolder> {

    private ArrayList<Room> rooms;
    private Context context;
    private DBHelper db;

    public RoomAdminAdapter(ArrayList<Room> rooms, Context context, DBHelper db) {
        this.rooms = rooms;
        this.context = context;
        this.db = db;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = rooms.get(position);
        holder.tvRoomNumber.setText("Room " + room.getRoomNumber());
        holder.tvRoomType.setText(room.getType());
        holder.tvRoomPrice.setText("$" + room.getPrice() + "/night");
        holder.tvRoomAvailability.setText(room.isAvailable() ? "Available" : "Unavailable");
        holder.tvRoomAvailability.setTextColor(context.getResources().getColor(room.isAvailable() ? R.color.success : R.color.error));
        holder.tvRoomView.setText(room.getType());
        holder.btnEdit.setOnClickListener(v -> {
            ((ManageRoomsActivity) context).showEditDialog(room);
        });
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Room")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        db.deleteRoom(room.getId());
                        rooms.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Room deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomNumber, tvRoomType, tvRoomPrice, tvRoomAvailability, tvRoomView;
        Button btnEdit, btnDelete;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomNumber = itemView.findViewById(R.id.tvAdminRoomNumber);
            tvRoomType = itemView.findViewById(R.id.tvAdminRoomType);
            tvRoomPrice = itemView.findViewById(R.id.tvAdminRoomPrice);
            tvRoomAvailability = itemView.findViewById(R.id.tvAdminRoomAvailability);
            tvRoomView = itemView.findViewById(R.id.tvAdminRoomView);
            btnEdit = itemView.findViewById(R.id.btnEditRoom);
            btnDelete = itemView.findViewById(R.id.btnDeleteRoom);
        }
    }
}
