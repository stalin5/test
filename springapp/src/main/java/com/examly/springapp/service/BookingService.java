package com.examly.springapp.service;

import com.examly.springapp.exception.BookingNotFoundException;
import com.examly.springapp.model.Booking;
import com.examly.springapp.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking addBooking(Booking booking) {
        // Simple fare calculation mock
        booking.setFare(200 + Math.random() * 100);
        if (booking.getDriverName() == null) {
            booking.setDriverName("Default Driver");
            booking.setBikeImage("bike.png");
        }
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
    return bookingRepository.findById(id)
            .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + id));
}

    public Booking updateBooking(Long id, Booking updatedBooking) {
        Booking existing = getBookingById(id);
        existing.setCustomerName(updatedBooking.getCustomerName());
        existing.setPickupLocation(updatedBooking.getPickupLocation());
        existing.setDropLocation(updatedBooking.getDropLocation());
        existing.setRideTime(updatedBooking.getRideTime());
        existing.setFare(updatedBooking.getFare());
        existing.setDriverName(updatedBooking.getDriverName());
        existing.setBikeImage(updatedBooking.getBikeImage());
        return bookingRepository.save(existing);
    }

    public void deleteBooking(Long id) {
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
    }
}
