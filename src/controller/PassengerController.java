package controller;

import dao.PassengerDAO;
import dao.impl.PassengerDAOImpl;
import model.Passenger;
import util.Validator;

import java.util.List;

public class PassengerController {
    private final PassengerDAO passengerDAO;

    public PassengerController() {
        this.passengerDAO = new PassengerDAOImpl();
    }

    public String validateAndAdd(String fullName, String cnic, String passportNumber, String gender, String phoneNumber, String email) {
        if (Validator.isEmpty(fullName) || Validator.isEmpty(cnic) || Validator.isEmpty(passportNumber) || 
            Validator.isEmpty(gender) || Validator.isEmpty(phoneNumber) || Validator.isEmpty(email)) {
            return "All fields are required!";
        }
        if (!Validator.isValidCNIC(cnic)) {
            return "Invalid CNIC format! Must be 13 digits (e.g., 12345-1234567-1 or 13 digits).";
        }
        if (!Validator.isValidEmail(email)) {
            return "Invalid Email format!";
        }
        if (!Validator.isValidPhone(phoneNumber)) {
            return "Invalid Phone number format (10-15 digits)!";
        }

        Passenger passenger = new Passenger(fullName, cnic, passportNumber, gender, phoneNumber, email);
        boolean success = passengerDAO.add(passenger);
        return success ? "SUCCESS" : "Failed to add passenger (CNIC, Passport, or Email may already exist)!";
    }

    public String validateAndUpdate(int id, String fullName, String cnic, String passportNumber, String gender, String phoneNumber, String email) {
        if (Validator.isEmpty(fullName) || Validator.isEmpty(cnic) || Validator.isEmpty(passportNumber) || 
            Validator.isEmpty(gender) || Validator.isEmpty(phoneNumber) || Validator.isEmpty(email)) {
            return "All fields are required!";
        }
        if (!Validator.isValidCNIC(cnic)) {
            return "Invalid CNIC format! Must be 13 digits (e.g., 12345-1234567-1 or 13 digits).";
        }
        if (!Validator.isValidEmail(email)) {
            return "Invalid Email format!";
        }
        if (!Validator.isValidPhone(phoneNumber)) {
            return "Invalid Phone number format (10-15 digits)!";
        }

        Passenger passenger = new Passenger(id, fullName, cnic, passportNumber, gender, phoneNumber, email);
        boolean success = passengerDAO.update(passenger);
        return success ? "SUCCESS" : "Failed to update passenger details!";
    }

    public boolean deletePassenger(int id) {
        return passengerDAO.delete(id);
    }

    public Passenger getPassenger(int id) {
        return passengerDAO.getById(id);
    }

    public List<Passenger> getAllPassengers() {
        return passengerDAO.getAll();
    }

    public List<Passenger> searchPassengers(String keyword) {
        return passengerDAO.search(keyword);
    }
}
