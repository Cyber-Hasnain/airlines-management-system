package Model;

public class Booking {
    private int bookingId;
    private int passengerId;
    private int flightId;
    private String seatNumber;
    private String bookingDate;
    private String ticketStatus;

    public Booking(int bookingId, int passengerId, int flightId, String seatNumber,
                   String bookingDate, String ticketStatus) {
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.bookingDate = bookingDate;
        this.ticketStatus = ticketStatus;
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

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", passengerId=" + passengerId +
                ", flightId=" + flightId +
                ", seatNumber='" + seatNumber + '\'' +
                ", bookingDate='" + bookingDate + '\'' +
                ", ticketStatus='" + ticketStatus + '\'' +
                '}';
    }
}

