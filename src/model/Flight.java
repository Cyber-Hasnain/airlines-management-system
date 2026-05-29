package model;

import java.sql.Date;

public class Flight {
    private int flightId;
    private String flightNumber;
    private String departureCity;
    private String arrivalCity;
    private String departureTime;
    private String arrivalTime;
    private Date flightDate;
    private int totalSeats;
    private int availableSeats;
    private double ticketPrice;

    public Flight() {}

    public Flight(int flightId, String flightNumber, String departureCity, String arrivalCity, String departureTime, String arrivalTime, Date flightDate, int totalSeats, int availableSeats, double ticketPrice) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.flightDate = flightDate;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.ticketPrice = ticketPrice;
    }

    public Flight(String flightNumber, String departureCity, String arrivalCity, String departureTime, String arrivalTime, Date flightDate, int totalSeats, int availableSeats, double ticketPrice) {
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.flightDate = flightDate;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.ticketPrice = ticketPrice;
    }

    public int getFlightId() { return flightId; }
    public void setFlightId(int flightId) { this.flightId = flightId; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getDepartureCity() { return departureCity; }
    public void setDepartureCity(String departureCity) { this.departureCity = departureCity; }

    public String getArrivalCity() { return arrivalCity; }
    public void setArrivalCity(String arrivalCity) { this.arrivalCity = arrivalCity; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public Date getFlightDate() { return flightDate; }
    public void setFlightDate(Date flightDate) { this.flightDate = flightDate; }

    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public double getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }

    @Override
    public String toString() {
        return flightNumber + " (" + departureCity + " -> " + arrivalCity + " | " + flightDate.toString() + " | Price: $" + ticketPrice + ")";
    }
}
