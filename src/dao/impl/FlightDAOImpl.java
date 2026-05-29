package dao.impl;

import dao.FlightDAO;
import model.Flight;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightDAOImpl implements FlightDAO {

    @Override
    public boolean add(Flight flight) {
        String sql = "INSERT INTO flights (flight_number, departure_city, arrival_city, departure_time, arrival_time, flight_date, total_seats, available_seats, ticket_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, flight.getFlightNumber());
            ps.setString(2, flight.getDepartureCity());
            ps.setString(3, flight.getArrivalCity());
            ps.setString(4, flight.getDepartureTime());
            ps.setString(5, flight.getArrivalTime());
            ps.setDate(6, flight.getFlightDate());
            ps.setInt(7, flight.getTotalSeats());
            ps.setInt(8, flight.getAvailableSeats());
            ps.setDouble(9, flight.getTicketPrice());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        flight.setFlightId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Flight flight) {
        String sql = "UPDATE flights SET flight_number = ?, departure_city = ?, arrival_city = ?, departure_time = ?, arrival_time = ?, flight_date = ?, total_seats = ?, available_seats = ?, ticket_price = ? WHERE flight_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, flight.getFlightNumber());
            ps.setString(2, flight.getDepartureCity());
            ps.setString(3, flight.getArrivalCity());
            ps.setString(4, flight.getDepartureTime());
            ps.setString(5, flight.getArrivalTime());
            ps.setDate(6, flight.getFlightDate());
            ps.setInt(7, flight.getTotalSeats());
            ps.setInt(8, flight.getAvailableSeats());
            ps.setDouble(9, flight.getTicketPrice());
            ps.setInt(10, flight.getFlightId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM flights WHERE flight_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Flight getById(int id) {
        String sql = "SELECT * FROM flights WHERE flight_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Flight(
                        rs.getInt("flight_id"),
                        rs.getString("flight_number"),
                        rs.getString("departure_city"),
                        rs.getString("arrival_city"),
                        rs.getString("departure_time"),
                        rs.getString("arrival_time"),
                        rs.getDate("flight_date"),
                        rs.getInt("total_seats"),
                        rs.getInt("available_seats"),
                        rs.getDouble("ticket_price")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Flight> getAll() {
        List<Flight> list = new ArrayList<>();
        String sql = "SELECT * FROM flights";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Flight(
                    rs.getInt("flight_id"),
                    rs.getString("flight_number"),
                    rs.getString("departure_city"),
                    rs.getString("arrival_city"),
                    rs.getString("departure_time"),
                    rs.getString("arrival_time"),
                    rs.getDate("flight_date"),
                    rs.getInt("total_seats"),
                    rs.getInt("available_seats"),
                    rs.getDouble("ticket_price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Flight> search(String keyword) {
        List<Flight> list = new ArrayList<>();
        String sql = "SELECT * FROM flights WHERE flight_number LIKE ? OR departure_city LIKE ? OR arrival_city LIKE ? OR flight_date LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String val = "%" + keyword + "%";
            ps.setString(1, val);
            ps.setString(2, val);
            ps.setString(3, val);
            ps.setString(4, val);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Flight(
                        rs.getInt("flight_id"),
                        rs.getString("flight_number"),
                        rs.getString("departure_city"),
                        rs.getString("arrival_city"),
                        rs.getString("departure_time"),
                        rs.getString("arrival_time"),
                        rs.getDate("flight_date"),
                        rs.getInt("total_seats"),
                        rs.getInt("available_seats"),
                        rs.getDouble("ticket_price")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
