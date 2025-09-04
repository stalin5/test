import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import BookingForm from "./components/BookingForm";
import BookingList from "./components/BookingList";

function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<BookingList />} />
        <Route path="/add" element={<BookingForm />} />
      </Routes>
    </Router>
  );
}

export default App;
