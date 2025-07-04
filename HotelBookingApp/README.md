# Hotel Room Booking System

A modern Android application that simulates hotel room reservations. Users can browse hotels, view room details, book rooms, and manage bookings. Admins can manage rooms and bookings.

## Features

### User Features
- **Authentication**: Login and registration system
- **Room Browsing**: View all available rooms with modern card-based UI
- **Room Details**: Detailed room information with amenities
- **Booking System**: Select dates, number of guests, and confirm bookings
- **My Bookings**: View and manage personal bookings
- **Search**: Search rooms by type
- **Modern UI**: Material Design with beautiful animations and transitions

### Admin Features
- **Room Management**: Add, edit, and delete rooms
- **Booking Management**: View all bookings and manage their status
- **Admin Dashboard**: Clean interface for hotel operations

## Technical Implementation

### Architecture
- **MVC Pattern**: Model-View-Controller architecture
- **SQLite Database**: Local database for data persistence
- **SharedPreferences**: Session management
- **RecyclerView**: Efficient list display
- **Material Design**: Modern UI components

### Key Components

#### Activities
- `MainActivity`: Splash screen and navigation
- `LoginActivity`: User authentication
- `RegisterActivity`: User registration
- `UserHomeActivity`: Main user interface
- `RoomDetailsActivity`: Room information and booking
- `MyBookingsActivity`: User's booking history
- `AdminHomeActivity`: Admin dashboard
- `ManageRoomsActivity`: Room management
- `ManageBookingsActivity`: Booking management

#### Models
- `User`: User data model
- `Room`: Room information model
- `Booking`: Booking data model

#### Database
- `DBHelper`: SQLite database helper with CRUD operations
- Tables: users, rooms, bookings

#### Adapters
- `RoomAdapter`: Room list display
- `BookingAdapter`: Booking list display
- `RoomAdminAdapter`: Admin room management
- `AdminBookingAdapter`: Admin booking management

### UI/UX Features
- **Material Design**: Modern, consistent design language
- **Color Scheme**: Professional blue and orange theme
- **Card-based Layout**: Clean, organized information display
- **Responsive Design**: Works on various screen sizes
- **Smooth Animations**: Enhanced user experience
- **Intuitive Navigation**: Easy-to-use interface

## Installation

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Build and run the application

## Usage

### For Users
1. Register a new account or login
2. Browse available rooms
3. Select a room to view details
4. Choose check-in/check-out dates
5. Select number of guests
6. Confirm booking
7. View bookings in "My Bookings"

### For Admins
1. Login with username: "ADMIN" and password: "ADMIN"
2. Access admin dashboard
3. Manage rooms (add, edit, delete)
4. Manage bookings (view, update status)

## Database Schema

### Users Table
- id (INTEGER PRIMARY KEY)
- username (TEXT)
- password (TEXT)

### Rooms Table
- id (INTEGER PRIMARY KEY)
- type (TEXT)
- price (REAL)
- imageUri (TEXT)
- available (INTEGER)

### Bookings Table
- id (INTEGER PRIMARY KEY)
- userId (INTEGER)
- roomId (INTEGER)
- checkIn (TEXT)
- checkOut (TEXT)
- guests (INTEGER)
- status (TEXT)

## Dependencies

- AndroidX AppCompat
- Material Design Components
- RecyclerView
- CardView
- SQLite

## Screenshots

The application features:
- Modern login/registration screens
- Beautiful room browsing interface
- Detailed room information pages
- Intuitive booking process
- Clean admin dashboard
- Professional booking management

## Future Enhancements

- Image upload for rooms
- Payment integration
- Push notifications
- Room availability calendar
- User reviews and ratings
- Multi-language support
- Dark theme support

## Contributing

This is a learning project demonstrating Android development concepts including:
- Activities and Navigation
- RecyclerView implementation
- SQLite database operations
- SharedPreferences for session management
- Material Design implementation
- Modern UI/UX practices

## License

This project is created for educational purposes to demonstrate Android development skills and concepts. 