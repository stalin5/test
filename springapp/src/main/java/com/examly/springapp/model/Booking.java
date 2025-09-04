package com.examly.springapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private String customerName;
    private String pickupLocation;
    private String dropLocation;
    private LocalDateTime rideTime;
    private double fare;
    private String driverName;
    private String bikeImage;

    // Default constructor
    public Booking() {}

    // Parameterized constructor
    public Booking(Long bookingId, String customerName, String pickupLocation,
                   String dropLocation, LocalDateTime rideTime,
                   double fare, String driverName, String bikeImage) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.rideTime = rideTime;
        this.fare = fare;
        this.driverName = driverName;
        this.bikeImage = bikeImage;
    }

    // Getters & Setters
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }

    public String getDropLocation() { return dropLocation; }
    public void setDropLocation(String dropLocation) { this.dropLocation = dropLocation; }

    public LocalDateTime getRideTime() { return rideTime; }
    public void setRideTime(LocalDateTime rideTime) { this.rideTime = rideTime; }

    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }

    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }

    public String getBikeImage() { return bikeImage; }
    public void setBikeImage(String bikeImage) { this.bikeImage = bikeImage; }
}
