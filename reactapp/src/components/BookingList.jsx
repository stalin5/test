import React, { useEffect, useState } from "react";
import BookingService from "../services/bookingService";
import BookingCard from "./BookingCard";
import { useNavigate } from "react-router-dom";

const BookingList = () => {
  const [bookings, setBookings] = useState([]);
  const navigate = useNavigate();

  const fetchBookings = async () => {
    try {
      const res = await BookingService.getAllBookings();
      setBookings(res.data);
    } catch (error) {
      console.error("Error fetching bookings:", error);
    }
  };

  useEffect(() => {
    fetchBookings();
  }, []);

  const deleteBooking = async (id) => {
    await BookingService.deleteBooking(id);
    fetchBookings();
  };

  const editBooking = (booking) => {
    navigate("/add", { state: booking });
  };

  return (
    <div className="container">
      <h2>All Bookings</h2>
      {bookings.length === 0 ? (
        <p>No bookings found</p>
      ) : (
        bookings.map((b) => (
          <BookingCard
            key={b.bookingId}
            booking={b}
            onDelete={deleteBooking}
            onEdit={editBooking}
          />
        ))
      )}
    </div>
  );
};

export default BookingList;
