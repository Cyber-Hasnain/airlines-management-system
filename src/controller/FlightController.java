package controller;

import dao.FlightDAO;
import dao.impl.FlightDAOImpl;
import model.Flight;
import util.Validator;

import java.sql.Date;
import java.util.List;

public class FlightController {
    private final FlightDAO flightDAO;

    public FlightController() {
        this.flightDAO = new FlightDAOImpl();
    }

    public String validateAndAdd(String flightNumber, String departureCity, String arrivalCity, 
                                 String departureTime, String arrivalTime, String flightDateStr, 
                                 String totalSeatsStr, String ticketPriceStr) {
        
        if (Validator.isEmpty(flightNumber) || Validator.isEmpty(departureCity) || Validator.isEmpty(arrivalCity) || 
            Validator.isEmpty(departureTime) || Validator.isEmpty(arrivalTime) || Validator.isEmpty(flightDateStr) || 
            Validator.isEmpty(totalSeatsStr) || Validator.isEmpty(ticketPriceStr)) {
            return "All fields are required!";
        }

        if (departureCity.equalsIgnoreCase(arrivalCity)) {
            return "Departure and Arrival cities cannot be the same!";
        }

        if (!departureTime.matches("^\\d{2}:\\d{2}$") || !arrivalTime.matches("^\\d{2}:\\d{2}$")) {
            return "Departure and Arrival times must be in HH:MM format!";
        }

        Date flightDate;
        try {
            flightDate = Date.valueOf(flightDateStr);
        } catch (IllegalArgumentException e) {
            return "Flight Date must be in YYYY-MM-DD format!";
        }

        if (!Validator.isPositiveInt(totalSeatsStr)) {
            return "Total Seats must be a positive integer!";
        }
        int totalSeats = Integer.parseInt(totalSeatsStr);

        if (!Validator.isPositiveDouble(ticketPriceStr)) {
            return "Ticket Price must be a positive number!";
        }
        double ticketPrice = Double.parseDouble(ticketPriceStr);

        Flight flight = new Flight(flightNumber, departureCity, arrivalCity, departureTime, arrivalTime, flightDate, totalSeats, totalSeats, ticketPrice);
        boolean success = flightDAO.add(flight);
        return success ? "SUCCESS" : "Failed to add flight (Flight Number may already exist)!";
    }

    public String validateAndUpdate(int flightId, String flightNumber, String departureCity, String arrivalCity, 
                                    String departureTime, String arrivalTime, String flightDateStr, 
                                    String totalSeatsStr, String availableSeatsStr, String ticketPriceStr) {
        
        if (Validator.isEmpty(flightNumber) || Validator.isEmpty(departureCity) || Validator.isEmpty(arrivalCity) || 
            Validator.isEmpty(departureTime) || Validator.isEmpty(arrivalTime) || Validator.isEmpty(flightDateStr) || 
            Validator.isEmpty(totalSeatsStr) || Validator.isEmpty(availableSeatsStr) || Validator.isEmpty(ticketPriceStr)) {
            return "All fields are required!";
        }

        if (departureCity.equalsIgnoreCase(arrivalCity)) {
            return "Departure and Arrival cities cannot be the same!";
        }

        if (!departureTime.matches("^\\d{2}:\\d{2}$") || !arrivalTime.matches("^\\d{2}:\\d{2}$")) {
            return "Departure and Arrival times must be in HH:MM format!";
        }

        Date flightDate;
        try {
            flightDate = Date.valueOf(flightDateStr);
        } catch (IllegalArgumentException e) {
            return "Flight Date must be in YYYY-MM-DD format!";
        }

        if (!Validator.isPositiveInt(totalSeatsStr) || !Validator.isPositiveInt(availableSeatsStr)) {
            return "Seats must be positive integers!";
        }
        int totalSeats = Integer.parseInt(totalSeatsStr);
        int availableSeats = Integer.parseInt(availableSeatsStr);
        if (availableSeats > totalSeats) {
            return "Available seats cannot exceed total seats!";
        }

        if (!Validator.isPositiveDouble(ticketPriceStr)) {
            return "Ticket Price must be a positive number!";
        }
        double ticketPrice = Double.parseDouble(ticketPriceStr);

        Flight flight = new Flight(flightId, flightNumber, departureCity, arrivalCity, departureTime, arrivalTime, flightDate, totalSeats, availableSeats, ticketPrice);
        boolean success = flightDAO.update(flight);
        return success ? "SUCCESS" : "Failed to update flight details!";
    }

    public boolean deleteFlight(int id) {
        return flightDAO.delete(id);
    }

    public Flight getFlight(int id) {
        return flightDAO.getById(id);
    }

    public List<Flight> getAllFlights() {
        return flightDAO.getAll();
    }

    public List<Flight> searchFlights(String keyword) {
        return flightDAO.search(keyword);
    }
}
