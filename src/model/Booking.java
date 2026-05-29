package model;

import java.sql.Timestamp;

public class Booking {
    private int bookingId;
    private int passengerId;
    private int flightId;
    private int seatNumber;
    private Timestamp bookingDate;
    private String ticketStatus;

    public Booking() {}

    public Booking(int bookingId, int passengerId, int flightId, int seatNumber, Timestamp bookingDate, String ticketStatus) {
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.bookingDate = bookingDate;
        this.ticketStatus = ticketStatus;
    }

    public Booking(int passengerId, int flightId, int seatNumber, String ticketStatus) {
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.ticketStatus = ticketStatus;
    }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }

    public int getFlightId() { return flightId; }
    public void setFlightId(int flightId) { this.flightId = flightId; }

    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }

    public Timestamp getBookingDate() { return bookingDate; }
    public void setBookingDate(Timestamp bookingDate) { this.bookingDate = bookingDate; }

    public String getTicketStatus() { return ticketStatus; }
    public void setTicketStatus(String ticketStatus) { this.ticketStatus = ticketStatus; }
}
