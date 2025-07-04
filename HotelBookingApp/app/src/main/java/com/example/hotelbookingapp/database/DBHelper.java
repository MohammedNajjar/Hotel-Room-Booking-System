package com.example.hotelbookingapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hotelbookingapp.models.Booking;
import com.example.hotelbookingapp.models.Room;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "HotelBooking.db";
    private static final int DB_VERSION = 2;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
        db.execSQL("CREATE TABLE rooms (id INTEGER PRIMARY KEY AUTOINCREMENT, roomNumber TEXT, type TEXT, price REAL, description TEXT, imageUri TEXT, available INTEGER)");
        db.execSQL("CREATE TABLE bookings (id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, roomId INTEGER, checkIn TEXT, checkOut TEXT, guests INTEGER, status TEXT)");
        db.execSQL("INSERT INTO rooms (roomNumber, type, price, description, imageUri, available) VALUES ('101', 'Deluxe Room', 150.0, 'Spacious deluxe room', '', 1)");
        db.execSQL("INSERT INTO rooms (roomNumber, type, price, description, imageUri, available) VALUES ('102', 'Suite', 250.0, 'Luxury suite with amenities', '', 1)");

        // Insert default admin
        db.execSQL("INSERT INTO users (username, password) VALUES ('ADMIN', 'ADMIN')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS rooms");
        db.execSQL("DROP TABLE IF EXISTS bookings");
        onCreate(db);
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    public boolean checkUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=?", new String[]{username});
        return cursor.getCount() > 0;
    }

    public ArrayList<Room> getAllAvailableRooms() {
        ArrayList<Room> roomList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM rooms WHERE available = 1", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String roomNumber = cursor.getString(1);
                String type = cursor.getString(2);
                double price = cursor.getDouble(3);
                String description = cursor.getString(4);
                String imageUri = cursor.getString(5);
                boolean available = cursor.getInt(6) == 1;

                roomList.add(new Room(id, roomNumber, type, price, description, imageUri, available));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return roomList;
    }
    public Room getRoomById(int roomId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM rooms WHERE id = ?", new String[]{String.valueOf(roomId)});
        if (cursor.moveToFirst()) {
            return new Room(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6) == 1
            );
        }
        return null;
    }
    public int getUserId(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM users WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return -1;
    }
    public boolean addBooking(int userId, int roomId, String checkIn, String checkOut, int guests) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("roomId", roomId);
        values.put("checkIn", checkIn);
        values.put("checkOut", checkOut);
        values.put("guests", guests);
        values.put("status", "Pending");
        long result = db.insert("bookings", null, values);
        return result != -1;
    }
    public ArrayList<Room> getAllRooms() {
        ArrayList<Room> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM rooms", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String roomNumber = cursor.getString(1);
                String type = cursor.getString(2);
                double price = cursor.getDouble(3);
                String description = cursor.getString(4);
                String imageUri = cursor.getString(5);
                boolean available = cursor.getInt(6) == 1;

                list.add(new Room(id, roomNumber, type, price, description, imageUri, available));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<Booking> getBookingsForUser(int userId) {
        ArrayList<Booking> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT b.id, r.type, b.checkIn, b.checkOut, b.guests " +
                "FROM bookings b INNER JOIN rooms r ON b.roomId = r.id " +
                "WHERE b.userId = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String roomType = cursor.getString(1);
                String checkIn = cursor.getString(2);
                String checkOut = cursor.getString(3);
                int guests = cursor.getInt(4);

                list.add(new Booking(id, roomType, checkIn, checkOut, guests));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public void addRoom(String roomNumber, String type, double price, String description, String imageUri, boolean available) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("roomNumber", roomNumber);
        cv.put("type", type);
        cv.put("price", price);
        cv.put("description", description);
        cv.put("imageUri", imageUri);
        cv.put("available", available ? 1 : 0);
        db.insert("rooms", null, cv);
    }
    public void updateRoom(int id, String roomNumber, String type, double price, String description, String imageUri, boolean available) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("roomNumber", roomNumber);
        cv.put("type", type);
        cv.put("price", price);
        cv.put("description", description);
        cv.put("imageUri", imageUri);
        cv.put("available", available ? 1 : 0);
        db.update("rooms", cv, "id=?", new String[]{String.valueOf(id)});
    }
    public void deleteRoom(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("rooms", "id=?", new String[]{String.valueOf(id)});
    }

    public ArrayList<Booking> getAllBookings() {
        ArrayList<Booking> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT b.id, u.username, r.type, b.checkIn, b.checkOut, b.guests, b.status " +
                "FROM bookings b " +
                "JOIN users u ON b.userId = u.id " +
                "JOIN rooms r ON b.roomId = r.id";

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(0);
                String username = c.getString(1);
                String roomType = c.getString(2);
                String checkIn = c.getString(3);
                String checkOut = c.getString(4);
                int guests = c.getInt(5);
                String status = c.getString(6);

                list.add(new Booking(id, username, roomType, checkIn, checkOut, guests, status));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public void updateBookingStatus(int bookingId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", status);
        db.update("bookings", cv, "id=?", new String[]{String.valueOf(bookingId)});
    }
    public void deleteBooking(int bookingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("bookings", "id=?", new String[]{String.valueOf(bookingId)});
    }

}

