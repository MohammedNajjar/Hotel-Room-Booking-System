package com.example.hotelbookingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.activities.RoomDetailsActivity;
import com.example.hotelbookingapp.models.Room;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private ArrayList<Room> roomList;
    private Context context;

    public RoomAdapter(ArrayList<Room> roomList, Context context) {
        this.roomList = roomList;
        this.context = context;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);

        holder.tvType.setText(room.getType());
        holder.tvPrice.setText("$" + String.format("%.0f", room.getPrice()) + "/night");
        
        // Set availability status
        if (room.isAvailable()) {
            holder.tvAvailability.setText("Available");
            holder.tvAvailability.setTextColor(context.getResources().getColor(R.color.success));
            holder.btnBookNow.setEnabled(true);
        } else {
            holder.tvAvailability.setText("Not Available");
            holder.tvAvailability.setTextColor(context.getResources().getColor(R.color.error));
            holder.btnBookNow.setEnabled(false);
        }

        // Placeholder for image
        holder.ivRoom.setImageResource(R.drawable.baseline_hotel_24);

        // Set click listeners
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RoomDetailsActivity.class);
            intent.putExtra("room_id", room.getId());
            context.startActivity(intent);
        });

        holder.btnBookNow.setOnClickListener(v -> {
            if (room.isAvailable()) {
                Intent intent = new Intent(context, RoomDetailsActivity.class);
                intent.putExtra("room_id", room.getId());
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "This room is not available", Toast.LENGTH_SHORT).show();
            }
        });

        TextView tvRoomNumber = holder.itemView.findViewById(R.id.tvRoomNumber);
        TextView tvRoomDescription = holder.itemView.findViewById(R.id.tvRoomDescription);
        tvRoomNumber.setText("Room #" + room.getRoomNumber());
        tvRoomDescription.setText(room.getDescription());
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvType, tvPrice, tvAvailability;
        ImageView ivRoom;
        MaterialButton btnBookNow;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tvRoomType);
            tvPrice = itemView.findViewById(R.id.tvRoomPrice);
            tvAvailability = itemView.findViewById(R.id.tvAvailability);
            ivRoom = itemView.findViewById(R.id.ivRoomImage);
            btnBookNow = itemView.findViewById(R.id.btnBookNow);
        }
    }
}
