package ui;

import controller.LoginController;
import util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardFrame extends JFrame {
    private JLabel passCountLabel;
    private JLabel flightCountLabel;
    private JLabel bookingCountLabel;
    private JLabel revenueLabel;

    public DashboardFrame() {
        setTitle("AeroUET-Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1020, 680);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIStyle.BG_DARK);

        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(240, 680));
        sidebar.setBackground(UIStyle.PANEL_DARK);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        JLabel logoLabel = new JLabel("AeroUET");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logoLabel.setForeground(UIStyle.ACCENT);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel roleLabel = new JLabel("Logged in as Administrator");
        roleLabel.setFont(UIStyle.FONT_SMALL);
        roleLabel.setForeground(UIStyle.TEXT_MUTED);
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(logoLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebar.add(roleLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));

        JButton btnPassengers = UIStyle.createStyledButton("Passengers", UIStyle.BG_DARK, UIStyle.ACCENT);
        JButton btnFlights = UIStyle.createStyledButton("Flights", UIStyle.BG_DARK, UIStyle.ACCENT);
        JButton btnBookings = UIStyle.createStyledButton("Bookings", UIStyle.BG_DARK, UIStyle.ACCENT);
        JButton btnPayments = UIStyle.createStyledButton("Payments", UIStyle.BG_DARK, UIStyle.ACCENT);
        JButton btnLogout = UIStyle.createStyledButton("Log Out", UIStyle.BG_DARK, UIStyle.DANGER);

        JButton[] buttons = {btnPassengers, btnFlights, btnBookings, btnPayments, btnLogout};
        for (JButton btn : buttons) {
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 12)));
        }

        btnPassengers.addActionListener(e -> new PassengerTableFrame().setVisible(true));
        btnFlights.addActionListener(e -> new FlightTableFrame().setVisible(true));
        btnBookings.addActionListener(e -> new BookTicketFrame().setVisible(true));
        btnPayments.addActionListener(e -> new PaymentFrame().setVisible(true));
        btnLogout.addActionListener(this::handleLogout);

        mainPanel.add(sidebar, BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel welcomePanel = new JPanel(new GridLayout(2, 1));
        welcomePanel.setOpaque(false);
        JLabel welcomeTitle = new JLabel("Welcome back, " + (LoginController.getCurrentUser() != null ? LoginController.getCurrentUser().getUsername() : "Admin") + "!");
        welcomeTitle.setFont(UIStyle.FONT_TITLE);
        welcomeTitle.setForeground(UIStyle.TEXT_LIGHT);
        JLabel welcomeDesc = new JLabel("Here is the real-time summary of your airline's current operations.");
        welcomeDesc.setFont(UIStyle.FONT_BODY);
        welcomeDesc.setForeground(UIStyle.TEXT_MUTED);
        welcomePanel.add(welcomeTitle);
        welcomePanel.add(welcomeDesc);
        centerPanel.add(welcomePanel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 25, 25));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        JPanel passCard = createStatCard("TOTAL PASSENGERS", "0", UIStyle.BUTTON_PRIMARY);
        JPanel flightCard = createStatCard("SCHEDULED FLIGHTS", "0", UIStyle.ACCENT);
        JPanel bookingCard = createStatCard("TOTAL BOOKINGS", "0", new Color(139, 92, 246)); 
        JPanel revenueCard = createStatCard("TOTAL REVENUE", "$0.00", new Color(245, 158, 11)); 

        gridPanel.add(passCard);
        gridPanel.add(flightCard);
        gridPanel.add(bookingCard);
        gridPanel.add(revenueCard);
        centerPanel.add(gridPanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);

        refreshStats();
    }

    private JPanel createStatCard(String title, String val, Color barColor) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(UIStyle.PANEL_DARK);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.setColor(barColor);
                g2d.fillRoundRect(0, 0, 8, getHeight(), 15, 15);
                g2d.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIStyle.FONT_SMALL);
        titleLabel.setForeground(UIStyle.TEXT_MUTED);

        JLabel valLabel = new JLabel(val);
        valLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        valLabel.setForeground(UIStyle.TEXT_LIGHT);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valLabel, BorderLayout.CENTER);

        if (title.contains("PASSENGERS")) passCountLabel = valLabel;
        else if (title.contains("FLIGHTS")) flightCountLabel = valLabel;
        else if (title.contains("BOOKINGS")) bookingCountLabel = valLabel;
        else if (title.contains("REVENUE")) revenueLabel = valLabel;

        return card;
    }

    public void refreshStats() {
        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM passengers");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) passCountLabel.setText(String.valueOf(rs.getInt(1)));
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM flights");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) flightCountLabel.setText(String.valueOf(rs.getInt(1)));
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM bookings");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) bookingCountLabel.setText(String.valueOf(rs.getInt(1)));
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT SUM(amount) FROM payments");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double amount = rs.getDouble(1);
                    revenueLabel.setText(String.format("$%,.2f", amount));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleLogout(ActionEvent e) {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            LoginController.logout();
            new LoginFrame().setVisible(true);
            this.dispose();
        }
    }
}
