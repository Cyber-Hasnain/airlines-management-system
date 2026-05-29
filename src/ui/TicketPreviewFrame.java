package ui;

import controller.BookingController;
import controller.FlightController;
import controller.PassengerController;
import model.Booking;
import model.Flight;
import model.Passenger;
import model.Ticket;

import javax.swing.*;
import java.awt.*;

public class TicketPreviewFrame extends JFrame {
    private BookingController bookingController;
    private PassengerController passengerController;
    private FlightController flightController;

    public TicketPreviewFrame(int bookingId) {
        bookingController = new BookingController();
        passengerController = new PassengerController();
        flightController = new FlightController();

        setTitle("AeroUET - Digital Boarding Pass");
        setSize(650, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        Booking booking = bookingController.getBooking(bookingId);
        if (booking == null) {
            JOptionPane.showMessageDialog(this, "Booking details could not be found!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        Passenger passenger = passengerController.getPassenger(booking.getPassengerId());
        Flight flight = flightController.getFlight(booking.getFlightId());
        Ticket ticket = bookingController.getTicketForBooking(bookingId);

        if (passenger == null || flight == null || ticket == null) {
            JOptionPane.showMessageDialog(this, "Unable to load complete ticket information!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(UIStyle.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Top Banner / Header
        JLabel headerLabel = new JLabel("AeroGlobal boarding pass", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerLabel.setForeground(UIStyle.ACCENT);
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Core Boarding Pass Ticket Card Panel
        JPanel passCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Card background
                g2.setColor(UIStyle.PANEL_DARK);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Border accent line
                g2.setColor(UIStyle.ACCENT);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                // Dotted tear line for ticket stub
                g2.setColor(new Color(71, 85, 105));
                float[] dash = {6f, 4f};
                g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dash, 0.0f));
                g2.drawLine(440, 10, 440, getHeight() - 10);
                
                g2.dispose();
            }
        };
        passCard.setOpaque(false);
        passCard.setLayout(null);

        // STUB LEFT: Main Boarding Pass
        JLabel deptCityLbl = new JLabel(flight.getDepartureCity().toUpperCase());
        deptCityLbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        deptCityLbl.setForeground(UIStyle.TEXT_LIGHT);
        deptCityLbl.setBounds(30, 25, 160, 35);
        passCard.add(deptCityLbl);

        JLabel arrowLbl = new JLabel("-->");
        arrowLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        arrowLbl.setForeground(UIStyle.ACCENT);
        arrowLbl.setBounds(195, 28, 40, 25);
        passCard.add(arrowLbl);

        JLabel arrCityLbl = new JLabel(flight.getArrivalCity().toUpperCase());
        arrCityLbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        arrCityLbl.setForeground(UIStyle.TEXT_LIGHT);
        arrCityLbl.setBounds(240, 25, 180, 35);
        passCard.add(arrCityLbl);

        // Details Grid
        addPassDetail(passCard, "PASSENGER NAME", passenger.getFullName(), 30, 80, 200);
        addPassDetail(passCard, "FLIGHT NUMBER", flight.getFlightNumber(), 240, 80, 180);
        
        addPassDetail(passCard, "DEPARTURE DATE", flight.getFlightDate().toString(), 30, 135, 120);
        addPassDetail(passCard, "DEPARTURE TIME", flight.getDepartureTime() + " (24h)", 165, 135, 120);
        addPassDetail(passCard, "SEAT ASSIGNMENT", "SEAT " + booking.getSeatNumber(), 295, 135, 130);

        addPassDetail(passCard, "TICKET NUMBER", "TKT-00" + ticket.getTicketId(), 30, 190, 150);
        addPassDetail(passCard, "RESERVATION STATUS", ticket.getTicketStatus().toUpperCase(), 200, 190, 150);

        // STUB RIGHT: Ticket Stub
        JLabel logoStub = new JLabel("AeroGlobal");
        logoStub.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logoStub.setForeground(UIStyle.ACCENT);
        logoStub.setBounds(460, 25, 140, 25);
        passCard.add(logoStub);

        addPassDetail(passCard, "PASSENGER", passenger.getFullName(), 460, 65, 140);
        addPassDetail(passCard, "FLIGHT", flight.getFlightNumber(), 460, 115, 140);
        addPassDetail(passCard, "SEAT NO", String.valueOf(booking.getSeatNumber()), 460, 165, 60);
        addPassDetail(passCard, "GATE", "OPEN", 530, 165, 60);
        addPassDetail(passCard, "TICKET ID", "#" + ticket.getTicketId(), 460, 215, 140);

        mainPanel.add(passCard, BorderLayout.CENTER);

        // Close Action button
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setOpaque(false);
        JButton closeBtn = UIStyle.createStyledButton("CLOSE PREVIEW", UIStyle.PANEL_DARK, UIStyle.DANGER);
        closeBtn.addActionListener(e -> dispose());
        btnPanel.add(closeBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void addPassDetail(JPanel panel, String label, String value, int x, int y, int width) {
        JLabel titleLbl = new JLabel(label);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        titleLbl.setForeground(UIStyle.TEXT_MUTED);
        titleLbl.setBounds(x, y, width, 15);
        panel.add(titleLbl);

        JLabel valLbl = new JLabel(value);
        valLbl.setFont(UIStyle.FONT_BODY);
        valLbl.setForeground(UIStyle.TEXT_LIGHT);
        valLbl.setBounds(x, y + 17, width, 20);
        panel.add(valLbl);
    }
}
