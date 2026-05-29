package dao.impl;

import dao.TicketDAO;
import model.Ticket;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAOImpl implements TicketDAO {

    @Override
    public boolean add(Ticket ticket) {
        String sql = "INSERT INTO tickets (booking_id, passenger_id, flight_id, seat_number, ticket_status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getBookingId());
            ps.setInt(2, ticket.getPassengerId());
            ps.setInt(3, ticket.getFlightId());
            ps.setInt(4, ticket.getSeatNumber());
            ps.setString(5, ticket.getTicketStatus());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        ticket.setTicketId(rs.getInt(1));
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
    public boolean update(Ticket ticket) {
        String sql = "UPDATE tickets SET booking_id = ?, passenger_id = ?, flight_id = ?, seat_number = ?, ticket_status = ? WHERE ticket_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ticket.getBookingId());
            ps.setInt(2, ticket.getPassengerId());
            ps.setInt(3, ticket.getFlightId());
            ps.setInt(4, ticket.getSeatNumber());
            ps.setString(5, ticket.getTicketStatus());
            ps.setInt(6, ticket.getTicketId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM tickets WHERE ticket_id = ?";
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
    public Ticket getById(int id) {
        String sql = "SELECT * FROM tickets WHERE ticket_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Ticket(
                        rs.getInt("ticket_id"),
                        rs.getInt("booking_id"),
                        rs.getInt("passenger_id"),
                        rs.getInt("flight_id"),
                        rs.getInt("seat_number"),
                        rs.getString("ticket_status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ticket> getAll() {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM tickets";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Ticket(
                    rs.getInt("ticket_id"),
                    rs.getInt("booking_id"),
                    rs.getInt("passenger_id"),
                    rs.getInt("flight_id"),
                    rs.getInt("seat_number"),
                    rs.getString("ticket_status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Ticket> search(String keyword) {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT t.* FROM tickets t " +
                     "JOIN passengers p ON t.passenger_id = p.passenger_id " +
                     "JOIN flights f ON t.flight_id = f.flight_id " +
                     "WHERE p.full_name LIKE ? OR f.flight_number LIKE ? OR t.ticket_status LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String val = "%" + keyword + "%";
            ps.setString(1, val);
            ps.setString(2, val);
            ps.setString(3, val);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Ticket(
                        rs.getInt("ticket_id"),
                        rs.getInt("booking_id"),
                        rs.getInt("passenger_id"),
                        rs.getInt("flight_id"),
                        rs.getInt("seat_number"),
                        rs.getString("ticket_status")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Ticket getByBookingId(int bookingId) {
        String sql = "SELECT * FROM tickets WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Ticket(
                        rs.getInt("ticket_id"),
                        rs.getInt("booking_id"),
                        rs.getInt("passenger_id"),
                        rs.getInt("flight_id"),
                        rs.getInt("seat_number"),
                        rs.getString("ticket_status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
