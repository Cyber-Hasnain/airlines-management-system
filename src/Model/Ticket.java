package Model;

public class Ticket {
    private int ticketId;
    private int bookingId;
    private int passengerId;
    private int flightId;
    private String seatNumber;
    private double ticketPrice;
    private String ticketStatus;

    public Ticket(int ticketId, int bookingId, int passengerId, int flightId,
                  String seatNumber, double ticketPrice, String ticketStatus) {
        this.ticketId = ticketId;
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.ticketPrice = ticketPrice;
        this.ticketStatus = ticketStatus;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", bookingId=" + bookingId +
                ", passengerId=" + passengerId +
                ", flightId=" + flightId +
                ", seatNumber='" + seatNumber + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", ticketStatus='" + ticketStatus + '\'' +
                '}';
    }
}