package controller;

import dao.PaymentDAO;
import dao.impl.PaymentDAOImpl;
import dao.BookingDAO;
import dao.impl.BookingDAOImpl;
import dao.FlightDAO;
import dao.impl.FlightDAOImpl;
import model.Booking;
import model.Flight;
import model.Payment;

import java.util.List;

public class PaymentController {
    private final PaymentDAO paymentDAO;
    private final BookingDAO bookingDAO;
    private final FlightDAO flightDAO;

    public PaymentController() {
        this.paymentDAO = new PaymentDAOImpl();
        this.bookingDAO = new BookingDAOImpl();
        this.flightDAO = new FlightDAOImpl();
    }

    public double calculateTotalPrice(int bookingId) {
        Booking booking = bookingDAO.getById(bookingId);
        if (booking != null) {
            Flight flight = flightDAO.getById(booking.getFlightId());
            if (flight != null) {
                return flight.getTicketPrice();
            }
        }
        return 0.0;
    }

    public String processPayment(int bookingId, String paymentMethod) {
        Booking booking = bookingDAO.getById(bookingId);
        if (booking == null) {
            return "Booking not found!";
        }
        
        Payment existingPayment = paymentDAO.getByBookingId(bookingId);
        if (existingPayment != null) {
            return "Payment already made for this booking!";
        }

        double amount = calculateTotalPrice(bookingId);
        if (amount <= 0) {
            return "Invalid payment amount!";
        }

        Payment payment = new Payment(bookingId, amount, paymentMethod);
        boolean success = paymentDAO.add(payment);
        return success ? "SUCCESS:" + payment.getPaymentId() : "Failed to process payment transaction.";
    }

    public Payment getPayment(int id) {
        return paymentDAO.getById(id);
    }

    public Payment getPaymentByBooking(int bookingId) {
        return paymentDAO.getByBookingId(bookingId);
    }

    public List<Payment> getAllPayments() {
        return paymentDAO.getAll();
    }

    public List<Payment> searchPayments(String keyword) {
        return paymentDAO.search(keyword);
    }
}
