package model;

public class Ticket {
    private int ticketId;
    private int bookingId;
    private int passengerId;
    private int flightId;
    private int seatNumber;
    private String ticketStatus;

    public Ticket() {}

    public Ticket(int ticketId, int bookingId, int passengerId, int flightId, int seatNumber, String ticketStatus) {
        this.ticketId = ticketId;
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.ticketStatus = ticketStatus;
    }

    public Ticket(int bookingId, int passengerId, int flightId, int seatNumber, String ticketStatus) {
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.ticketStatus = ticketStatus;
    }

    public int getTicketId() { return ticketId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }

    public int getFlightId() { return flightId; }
    public void setFlightId(int flightId) { this.flightId = flightId; }

    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }

    public String getTicketStatus() { return ticketStatus; }
    public void setTicketStatus(String ticketStatus) { this.ticketStatus = ticketStatus; }
}
