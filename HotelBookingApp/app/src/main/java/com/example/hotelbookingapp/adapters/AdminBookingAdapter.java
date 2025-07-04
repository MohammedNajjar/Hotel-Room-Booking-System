package com.example.hotelbookingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.database.DBHelper;
import com.example.hotelbookingapp.models.Booking;

import java.util.ArrayList;

public class AdminBookingAdapter extends RecyclerView.Adapter<AdminBookingAdapter.BookingViewHolder> {

    private ArrayList<Booking> list;
    private Context context;
    private DBHelper db;

    public AdminBookingAdapter(ArrayList<Booking> list, Context context, DBHelper db) {
        this.list = list;
        this.context = context;
        this.db = db;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking b = list.get(position);

        holder.tvUser.setText("User: " + b.getUsername());
        holder.tvRoom.setText("Room: " + b.getRoomType());
        holder.tvDates.setText("From: " + b.getCheckIn() + " To: " + b.getCheckOut());
        holder.tvGuests.setText("Guests: " + b.getGuests());
        holder.tvStatus.setText("Status: " + b.getStatus());

        holder.btnConfirm.setOnClickListener(v -> {
            db.updateBookingStatus(b.getId(), "Confirmed");
            b.setStatus("Confirmed");
            notifyItemChanged(position);
        });

        holder.btnCancel.setOnClickListener(v -> {
            db.updateBookingStatus(b.getId(), "Cancelled");
            b.setStatus("Cancelled");
            notifyItemChanged(position);
        });

        holder.btnDelete.setOnClickListener(v -> {
            db.deleteBooking(b.getId());
            list.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvUser, tvRoom, tvDates, tvGuests, tvStatus;
        Button btnConfirm, btnCancel, btnDelete;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUser = itemView.findViewById(R.id.tvAdminUser);
            tvRoom = itemView.findViewById(R.id.tvAdminRoom);
            tvDates = itemView.findViewById(R.id.tvAdminDates);
            tvGuests = itemView.findViewById(R.id.tvAdminGuests);
            tvStatus = itemView.findViewById(R.id.tvAdminStatus);
            btnConfirm = itemView.findViewById(R.id.btnConfirm);
            btnCancel = itemView.findViewById(R.id.btnCancel);
            btnDelete = itemView.findViewById(R.id.btnDeleteBooking);
        }
    }
}
