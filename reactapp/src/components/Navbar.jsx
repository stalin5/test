import React from "react";
import { Link } from "react-router-dom";

const Navbar = () => {
  return (
    <nav className="navbar">
      <h2>🚖 Booking App</h2>
      <div>
        <Link to="/">Bookings</Link>
        <Link to="/add">Add Booking</Link>
      </div>
    </nav>
  );
};

export default Navbar;
