import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import BookingService from "../services/bookingService";

const BookingForm = () => {
  const [booking, setBooking] = useState({
    customerName: "",
    pickupLocation: "",
    dropLocation: "",
    rideTime: "",
  });

  const navigate = useNavigate();
  const location = useLocation();

  // If editing, pre-fill form
  useEffect(() => {
    if (location.state) {
      setBooking(location.state);
    }
  }, [location.state]);

  const handleChange = (e) => {
    setBooking({ ...booking, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (booking.bookingId) {
      await BookingService.updateBooking(booking.bookingId, booking);
    } else {
      await BookingService.addBooking(booking);
    }
    navigate("/");
  };

  return (
    <div className="container">
      <h2>{booking.bookingId ? "Edit Booking" : "Add Booking"}</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="customerName"
          placeholder="Customer Name"
          value={booking.customerName}
          onChange={handleChange}
          required
        />
        <input
          type="text"
          name="pickupLocation"
          placeholder="Pickup Location"
          value={booking.pickupLocation}
          onChange={handleChange}
          required
        />
        <input
          type="text"
          name="dropLocation"
          placeholder="Drop Location"
          value={booking.dropLocation}
          onChange={handleChange}
          required
        />
        <input
          type="datetime-local"
          name="rideTime"
          value={booking.rideTime}
          onChange={handleChange}
          required
        />
        <button type="submit">
          {booking.bookingId ? "Update Booking" : "Save Booking"}
        </button>
      </form>
    </div>
  );
};

export default BookingForm;
