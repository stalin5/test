import axios from "axios";

const API_URL = "https://8080-fddecedccde329052728bccfaccecftwo.premiumproject.examly.io/api/bookings";

class BookingService {
  getAllBookings() {
    return axios.get(API_URL);
  }

  getBookingById(id) {
    return axios.get(`${API_URL}/${id}`);
  }

  addBooking(booking) {
    return axios.post(API_URL, booking);
  }

  updateBooking(id, booking) {
    return axios.put(`${API_URL}/${id}`, booking);
  }

  deleteBooking(id) {
    return axios.delete(`${API_URL}/${id}`);
  }
}

export default new BookingService();
