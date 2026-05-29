package dao.impl;

import dao.BookingDAO;
import model.Booking;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    @Override
    public boolean add(Booking booking) {
        String insertSql = "INSERT INTO bookings (passenger_id, flight_id, seat_number, ticket_status) VALUES (?, ?, ?, ?)";
        String updateFlightSql = "UPDATE flights SET available_seats = available_seats - 1 WHERE flight_id = ? AND available_seats > 0";
        
        Connection conn = null;
        PreparedStatement psInsert = null;
        PreparedStatement psUpdateFlight = null;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            psInsert = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            psInsert.setInt(1, booking.getPassengerId());
            psInsert.setInt(2, booking.getFlightId());
            psInsert.setInt(3, booking.getSeatNumber());
            psInsert.setString(4, booking.getTicketStatus());
            
            int affectedRows = psInsert.executeUpdate();
            if (affectedRows == 0) {
                conn.rollback();
                return false;
            }
            
            try (ResultSet rs = psInsert.getGeneratedKeys()) {
                if (rs.next()) {
                    booking.setBookingId(rs.getInt(1));
                }
            }
            
            psUpdateFlight = conn.prepareStatement(updateFlightSql);
            psUpdateFlight.setInt(1, booking.getFlightId());
            int flightRows = psUpdateFlight.executeUpdate();
            if (flightRows == 0) {
                conn.rollback();
                return false;
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        } finally {
            if (psInsert != null) try { psInsert.close(); } catch (SQLException e) {}
            if (psUpdateFlight != null) try { psUpdateFlight.close(); } catch (SQLException e) {}
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {}
            }
        }
        return false;
    }

    @Override
    public boolean update(Booking booking) {
        String sql = "UPDATE bookings SET passenger_id = ?, flight_id = ?, seat_number = ?, ticket_status = ? WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, booking.getPassengerId());
            ps.setInt(2, booking.getFlightId());
            ps.setInt(3, booking.getSeatNumber());
            ps.setString(4, booking.getTicketStatus());
            ps.setInt(5, booking.getBookingId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String selectBooking = "SELECT flight_id, ticket_status FROM bookings WHERE booking_id = ?";
        String deleteSql = "DELETE FROM bookings WHERE booking_id = ?";
        String updateFlightSql = "UPDATE flights SET available_seats = available_seats + 1 WHERE flight_id = ?";
        
        Connection conn = null;
        PreparedStatement psSelect = null;
        PreparedStatement psDelete = null;
        PreparedStatement psUpdateFlight = null;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            psSelect = conn.prepareStatement(selectBooking);
            psSelect.setInt(1, id);
            int flightId = -1;
            String status = "";
            try (ResultSet rs = psSelect.executeQuery()) {
                if (rs.next()) {
                    flightId = rs.getInt("flight_id");
                    status = rs.getString("ticket_status");
                }
            }
            
            if (flightId == -1) {
                conn.rollback();
                return false;
            }
            
            psDelete = conn.prepareStatement(deleteSql);
            psDelete.setInt(1, id);
            int rowsDeleted = psDelete.executeUpdate();
            if (rowsDeleted == 0) {
                conn.rollback();
                return false;
            }
            
            if (!"CANCELLED".equalsIgnoreCase(status)) {
                psUpdateFlight = conn.prepareStatement(updateFlightSql);
                psUpdateFlight.setInt(1, flightId);
                psUpdateFlight.executeUpdate();
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        } finally {
            if (psSelect != null) try { psSelect.close(); } catch (SQLException e) {}
            if (psDelete != null) try { psDelete.close(); } catch (SQLException e) {}
            if (psUpdateFlight != null) try { psUpdateFlight.close(); } catch (SQLException e) {}
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {}
            }
        }
        return false;
    }

    @Override
    public Booking getById(int id) {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("passenger_id"),
                        rs.getInt("flight_id"),
                        rs.getInt("seat_number"),
                        rs.getTimestamp("booking_date"),
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
    public List<Booking> getAll() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("passenger_id"),
                    rs.getInt("flight_id"),
                    rs.getInt("seat_number"),
                    rs.getTimestamp("booking_date"),
                    rs.getString("ticket_status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Booking> search(String keyword) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.* FROM bookings b " +
                     "JOIN passengers p ON b.passenger_id = p.passenger_id " +
                     "JOIN flights f ON b.flight_id = f.flight_id " +
                     "WHERE p.full_name LIKE ? OR f.flight_number LIKE ? OR b.ticket_status LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String val = "%" + keyword + "%";
            ps.setString(1, val);
            ps.setString(2, val);
            ps.setString(3, val);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("passenger_id"),
                        rs.getInt("flight_id"),
                        rs.getInt("seat_number"),
                        rs.getTimestamp("booking_date"),
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
    public boolean isSeatOccupied(int flightId, int seatNumber) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE flight_id = ? AND seat_number = ? AND ticket_status != 'CANCELLED'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, flightId);
            ps.setInt(2, seatNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
