package com.example.hotelbookingapp.models;
public class Booking {
    private int id;
    private String username;
    private String roomType;
    private String checkIn;
    private String checkOut;
    private int guests;
    private String status;

    // For Admin: Full booking with username + status
    public Booking(int id, String username, String roomType, String checkIn, String checkOut, int guests, String status) {
        this.id = id;
        this.username = username;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guests = guests;
        this.status = status;
    }

    // ✅ For User: Simplified constructor
    public Booking(int id, String roomType, String checkIn, String checkOut, int guests) {
        this.id = id;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guests = guests;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public int getGuests() {
        return guests;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
