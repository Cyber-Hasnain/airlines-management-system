package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("=== TESTING DATABASE CONNECTION ===");
        try {
            Connection conn = DBConnection.getConnection();
            System.out.println("SUCCESS: Connection established successfully!");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            System.out.println("SUCCESS: Table 'users' queried successfully. Rows found:");
            while (rs.next()) {
                System.out.printf(" - ID: %d | Username: %s | Role: %s\n",
                    rs.getInt("id"), rs.getString("username"), rs.getString("role"));
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("FAILURE: Database connection failed!");
            e.printStackTrace();
        }
    }
}
