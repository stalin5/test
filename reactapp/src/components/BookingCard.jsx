import React from "react";

const BookingCard = ({ booking, onDelete, onEdit }) => {
  return (
    <div className="card">
      <h3>{booking.customerName}</h3>
      <p><b>Pickup:</b> {booking.pickupLocation}</p>
      <p><b>Drop:</b> {booking.dropLocation}</p>
      <p><b>Ride Time:</b> {new Date(booking.rideTime).toLocaleString()}</p>
      <p><b>Fare:</b> ₹{booking.fare?.toFixed(2)}</p>
      <p><b>Driver:</b> {booking.driverName}</p>
      <div>
        <button onClick={() => onEdit(booking)}>✏️ Edit</button>
        <button onClick={() => onDelete(booking.bookingId)}>🗑️ Delete</button>
      </div>
    </div>
  );
};

export default BookingCard;
