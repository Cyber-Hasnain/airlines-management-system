package ui;

import controller.BookingController;
import controller.FlightController;
import controller.PassengerController;
import controller.PaymentController;
import model.Booking;
import model.Flight;
import model.Passenger;
import model.Payment;

import javax.swing.*;
import java.awt.*;

public class ReceiptFrame extends JFrame {
    private PaymentController paymentController;
    private BookingController bookingController;
    private PassengerController passengerController;
    private FlightController flightController;

    public ReceiptFrame(int paymentId) {
        paymentController = new PaymentController();
        bookingController = new BookingController();
        passengerController = new PassengerController();
        flightController = new FlightController();

        setTitle("AeroUET - Official Transaction Receipt");
        setSize(480, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        Payment payment = paymentController.getPayment(paymentId);
        if (payment == null) {
            JOptionPane.showMessageDialog(this, "Transaction receipt details could not be found!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        Booking booking = bookingController.getBooking(payment.getBookingId());
        if (booking == null) {
            JOptionPane.showMessageDialog(this, "Associated booking details could not be loaded!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        Passenger passenger = passengerController.getPassenger(booking.getPassengerId());
        Flight flight = flightController.getFlight(booking.getFlightId());

        if (passenger == null || flight == null) {
            JOptionPane.showMessageDialog(this, "Failed to load matching passenger/flight details!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(UIStyle.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // Header Title
        JLabel titleLabel = new JLabel("Official Payment Receipt", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(UIStyle.ACCENT);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Core Receipt Panel
        JPanel receiptCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw panel background
                g2.setColor(UIStyle.PANEL_DARK);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Draw border
                g2.setColor(new Color(71, 85, 105));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                
                // Decorative divider
                g2.drawLine(20, 185, getWidth() - 20, 185);
                g2.drawLine(20, 375, getWidth() - 20, 375);

                g2.dispose();
            }
        };
        receiptCard.setOpaque(false);
        receiptCard.setLayout(null);

        // STAMP / LOGO
        JLabel logoLbl = new JLabel("AeroUET");
        logoLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logoLbl.setForeground(UIStyle.ACCENT);
        logoLbl.setBounds(25, 20, 150, 30);
        receiptCard.add(logoLbl);

        JLabel statusLbl = new JLabel("PAID", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(16, 185, 129, 40)); // Semi-transparent emerald
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        statusLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusLbl.setForeground(UIStyle.ACCENT);
        statusLbl.setBounds(300, 20, 90, 30);
        receiptCard.add(statusLbl);

        // Section 1: Transaction Metadata
        addReceiptField(receiptCard, "TRANSACTION ID", "#TXN-00" + payment.getPaymentId(), 25, 75, 170);
        addReceiptField(receiptCard, "DATE & TIME", payment.getPaymentDate() != null ? payment.getPaymentDate().toString() : "Recent", 215, 75, 180);
        addReceiptField(receiptCard, "PAYMENT METHOD", payment.getPaymentMethod(), 25, 125, 170);
        addReceiptField(receiptCard, "RESERVATION ID", "#RSV-00" + booking.getBookingId(), 215, 125, 180);

        // Section 2: Flight/Passenger details
        addReceiptField(receiptCard, "PASSENGER FULL NAME", passenger.getFullName(), 25, 200, 350);
        addReceiptField(receiptCard, "PASSPORT NUMBER", passenger.getPassportNumber(), 25, 250, 170);
        addReceiptField(receiptCard, "CNIC NUMBER", passenger.getCnic(), 215, 250, 180);
        addReceiptField(receiptCard, "FLIGHT & ROUTE", flight.getFlightNumber() + " (" + flight.getDepartureCity() + " -> " + flight.getArrivalCity() + ")", 25, 300, 350);

        // Section 3: Summary details
        addReceiptField(receiptCard, "SEAT ASSIGNED", "SEAT " + booking.getSeatNumber(), 25, 395, 150);
        
        JLabel totalLabel = new JLabel("TOTAL AMOUNT PAID");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        totalLabel.setForeground(UIStyle.TEXT_MUTED);
        totalLabel.setBounds(215, 395, 180, 15);
        receiptCard.add(totalLabel);

        JLabel totalVal = new JLabel(String.format("$%.2f", payment.getAmount()));
        totalVal.setFont(new Font("Segoe UI", Font.BOLD, 22));
        totalVal.setForeground(new Color(245, 158, 11)); // Amber
        totalVal.setBounds(215, 412, 180, 25);
        receiptCard.add(totalVal);

        mainPanel.add(receiptCard, BorderLayout.CENTER);

        // Close button
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setOpaque(false);
        JButton closeBtn = UIStyle.createStyledButton("CLOSE RECEIPT", UIStyle.PANEL_DARK, UIStyle.DANGER);
        closeBtn.addActionListener(e -> dispose());
        btnPanel.add(closeBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void addReceiptField(JPanel panel, String title, String val, int x, int y, int width) {
        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        titleLbl.setForeground(UIStyle.TEXT_MUTED);
        titleLbl.setBounds(x, y, width, 15);
        panel.add(titleLbl);

        JLabel valLbl = new JLabel(val);
        valLbl.setFont(UIStyle.FONT_BODY);
        valLbl.setForeground(UIStyle.TEXT_LIGHT);
        valLbl.setBounds(x, y + 17, width, 20);
        panel.add(valLbl);
    }
}
