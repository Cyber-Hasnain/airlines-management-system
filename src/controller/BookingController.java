package controller;

import dao.BookingDAO;
import dao.impl.BookingDAOImpl;
import dao.FlightDAO;
import dao.impl.FlightDAOImpl;
import dao.TicketDAO;
import dao.impl.TicketDAOImpl;
import model.Booking;
import model.Flight;
import model.Ticket;

import java.util.List;

public class BookingController {
    private final BookingDAO bookingDAO;
    private final FlightDAO flightDAO;
    private final TicketDAO ticketDAO;

    public BookingController() {
        this.bookingDAO = new BookingDAOImpl();
        this.flightDAO = new FlightDAOImpl();
        this.ticketDAO = new TicketDAOImpl();
    }

    public String bookTicket(int passengerId, int flightId, int seatNumber) {
        Flight flight = flightDAO.getById(flightId);
        if (flight == null) {
            return "Flight not found!";
        }
        if (flight.getAvailableSeats() <= 0) {
            return "No seats available on this flight!";
        }
        if (seatNumber <= 0 || seatNumber > flight.getTotalSeats()) {
            return "Invalid seat number! Flight total seats: " + flight.getTotalSeats();
        }

        if (bookingDAO.isSeatOccupied(flightId, seatNumber)) {
            return "Seat " + seatNumber + " is already occupied on this flight!";
        }

        Booking booking = new Booking(passengerId, flightId, seatNumber, "CONFIRMED");
        boolean success = bookingDAO.add(booking);
        if (success) {
            Ticket ticket = new Ticket(booking.getBookingId(), passengerId, flightId, seatNumber, "ACTIVE");
            ticketDAO.add(ticket);
            return "SUCCESS:" + booking.getBookingId();
        }
        
        return "Failed to complete booking! The seat might have been booked in the meantime.";
    }

    public boolean cancelTicket(int bookingId) {
        Booking booking = bookingDAO.getById(bookingId);
        if (booking != null) {
            booking.setTicketStatus("CANCELLED");
            bookingDAO.update(booking);
            
            Ticket ticket = ticketDAO.getByBookingId(bookingId);
            if (ticket != null) {
                ticket.setTicketStatus("CANCELLED");
                ticketDAO.update(ticket);
            }
            
            return bookingDAO.delete(bookingId);
        }
        return false;
    }

    public Booking getBooking(int id) {
        return bookingDAO.getById(id);
    }

    public List<Booking> getAllBookings() {
        return bookingDAO.getAll();
    }

    public List<Booking> searchBookings(String keyword) {
        return bookingDAO.search(keyword);
    }

    public Ticket getTicketForBooking(int bookingId) {
        return ticketDAO.getByBookingId(bookingId);
    }
}
