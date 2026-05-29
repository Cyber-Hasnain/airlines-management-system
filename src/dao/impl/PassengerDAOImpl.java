package dao.impl;

import dao.PassengerDAO;
import model.Passenger;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerDAOImpl implements PassengerDAO {

    @Override
    public boolean add(Passenger passenger) {
        String sql = "INSERT INTO passengers (full_name, cnic, passport_number, gender, phone_number, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, passenger.getFullName());
            ps.setString(2, passenger.getCnic());
            ps.setString(3, passenger.getPassportNumber());
            ps.setString(4, passenger.getGender());
            ps.setString(5, passenger.getPhoneNumber());
            ps.setString(6, passenger.getEmail());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        passenger.setPassengerId(rs.getInt(1));
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
    public boolean update(Passenger passenger) {
        String sql = "UPDATE passengers SET full_name = ?, cnic = ?, passport_number = ?, gender = ?, phone_number = ?, email = ? WHERE passenger_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, passenger.getFullName());
            ps.setString(2, passenger.getCnic());
            ps.setString(3, passenger.getPassportNumber());
            ps.setString(4, passenger.getGender());
            ps.setString(5, passenger.getPhoneNumber());
            ps.setString(6, passenger.getEmail());
            ps.setInt(7, passenger.getPassengerId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM passengers WHERE passenger_id = ?";
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
    public Passenger getById(int id) {
        String sql = "SELECT * FROM passengers WHERE passenger_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Passenger(
                        rs.getInt("passenger_id"),
                        rs.getString("full_name"),
                        rs.getString("cnic"),
                        rs.getString("passport_number"),
                        rs.getString("gender"),
                        rs.getString("phone_number"),
                        rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Passenger> getAll() {
        List<Passenger> list = new ArrayList<>();
        String sql = "SELECT * FROM passengers";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Passenger(
                    rs.getInt("passenger_id"),
                    rs.getString("full_name"),
                    rs.getString("cnic"),
                    rs.getString("passport_number"),
                    rs.getString("gender"),
                    rs.getString("phone_number"),
                    rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Passenger> search(String keyword) {
        List<Passenger> list = new ArrayList<>();
        String sql = "SELECT * FROM passengers WHERE full_name LIKE ? OR cnic LIKE ? OR passport_number LIKE ? OR email LIKE ? OR phone_number LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String val = "%" + keyword + "%";
            ps.setString(1, val);
            ps.setString(2, val);
            ps.setString(3, val);
            ps.setString(4, val);
            ps.setString(5, val);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Passenger(
                        rs.getInt("passenger_id"),
                        rs.getString("full_name"),
                        rs.getString("cnic"),
                        rs.getString("passport_number"),
                        rs.getString("gender"),
                        rs.getString("phone_number"),
                        rs.getString("email")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
