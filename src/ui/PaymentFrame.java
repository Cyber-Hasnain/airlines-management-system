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
import java.awt.event.ActionEvent;

public class PaymentFrame extends JFrame {
    private JTextField bookingIdField;
    private JLabel passengerNameLbl;
    private JLabel flightNoLbl;
    private JLabel routeLbl;
    private JLabel amountLbl;
    private JComboBox<String> paymentMethodCombo;
    private JButton processBtn;
    private JButton loadBtn;

    private PaymentController paymentController;
    private BookingController bookingController;
    private PassengerController passengerController;
    private FlightController flightController;

    private int selectedBookingId = -1;
    private boolean isLockedMode = false;

    // Fresh manual checkout constructor
    public PaymentFrame() {
        initControllers();
        buildUI();
    }

    // Locked pre-selected checkout constructor
    public PaymentFrame(int bookingId) {
        initControllers();
        this.selectedBookingId = bookingId;
        this.isLockedMode = true;
        buildUI();
        loadBookingDetails(bookingId);
    }

    private void initControllers() {
        paymentController = new PaymentController();
        bookingController = new BookingController();
        passengerController = new PassengerController();
        flightController = new FlightController();
    }

    private void buildUI() {
        setTitle("AeroUET - Secure Payment Terminal");
        setSize(480, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIStyle.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // Header Title
        JLabel titleLabel = new JLabel("Secure Payment Terminal", SwingConstants.CENTER);
        titleLabel.setFont(UIStyle.FONT_TITLE);
        titleLabel.setForeground(UIStyle.ACCENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Content panel (Form)
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(6, 0, 3, 0);

        // Booking ID Input
        gbc.gridy = 0;
        contentPanel.add(createFieldLabel("Reservation / Booking ID"), gbc);
        gbc.gridy = 1;
        
        JPanel lookupPanel = new JPanel(new BorderLayout(10, 0));
        lookupPanel.setOpaque(false);
        bookingIdField = new JTextField();
        UIStyle.styleTextField(bookingIdField);
        lookupPanel.add(bookingIdField, BorderLayout.CENTER);
        
        loadBtn = UIStyle.createStyledButton("LOAD", UIStyle.BUTTON_PRIMARY, UIStyle.BUTTON_PRIMARY_HOVER);
        loadBtn.setFont(UIStyle.FONT_SMALL);
        loadBtn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        loadBtn.addActionListener(this::handleLoadBooking);
        lookupPanel.add(loadBtn, BorderLayout.EAST);
        contentPanel.add(lookupPanel, gbc);

        if (isLockedMode) {
            bookingIdField.setText(String.valueOf(selectedBookingId));
            bookingIdField.setEditable(false);
            loadBtn.setEnabled(false);
        }

        // Divider
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        contentPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
        gbc.insets = new Insets(6, 0, 3, 0);

        // Details Panel (renders once loaded)
        gbc.gridy = 3;
        JPanel detailsBox = new JPanel(new GridLayout(4, 2, 5, 8));
        detailsBox.setBackground(UIStyle.PANEL_DARK);
        detailsBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(51, 65, 85), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        detailsBox.add(createDetailLabel("Passenger:"));
        passengerNameLbl = createDetailValue("Not Loaded");
        detailsBox.add(passengerNameLbl);

        detailsBox.add(createDetailLabel("Flight No:"));
        flightNoLbl = createDetailValue("Not Loaded");
        detailsBox.add(flightNoLbl);

        detailsBox.add(createDetailLabel("Flight Route:"));
        routeLbl = createDetailValue("Not Loaded");
        detailsBox.add(routeLbl);

        detailsBox.add(createDetailLabel("Amount Due:"));
        amountLbl = new JLabel("$0.00");
        amountLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        amountLbl.setForeground(new Color(245, 158, 11)); // Amber
        detailsBox.add(amountLbl);

        contentPanel.add(detailsBox, gbc);

        // Payment Method Combo
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 3, 0);
        contentPanel.add(createFieldLabel("Payment Method Choice"), gbc);
        gbc.gridy = 5;
        gbc.insets = new Insets(6, 0, 3, 0);
        String[] methods = {"Credit Card", "Cash Payment", "Online Banking Transfer"};
        paymentMethodCombo = new JComboBox<>(methods);
        paymentMethodCombo.setBackground(new Color(51, 65, 85));
        paymentMethodCombo.setForeground(UIStyle.TEXT_LIGHT);
        paymentMethodCombo.setFont(UIStyle.FONT_BODY);
        contentPanel.add(paymentMethodCombo, gbc);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        UIStyle.styleScrollPane(scrollPane);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer Action Panel
        JPanel footerPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        footerPanel.setOpaque(false);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        processBtn = UIStyle.createStyledButton("PROCESS", UIStyle.ACCENT, UIStyle.ACCENT_HOVER);
        processBtn.setEnabled(false); // Enable only after booking loaded successfully
        JButton cancelBtn = UIStyle.createStyledButton("CANCEL", UIStyle.PANEL_DARK, UIStyle.DANGER);

        processBtn.addActionListener(this::handleProcessPayment);
        cancelBtn.addActionListener(e -> dispose());

        footerPanel.add(processBtn);
        footerPanel.add(cancelBtn);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JLabel createFieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(UIStyle.FONT_SMALL);
        lbl.setForeground(UIStyle.TEXT_MUTED);
        return lbl;
    }

    private JLabel createDetailLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(UIStyle.FONT_SMALL);
        lbl.setForeground(UIStyle.TEXT_MUTED);
        return lbl;
    }

    private JLabel createDetailValue(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(UIStyle.FONT_BODY);
        lbl.setForeground(UIStyle.TEXT_LIGHT);
        return lbl;
    }

    private void handleLoadBooking(ActionEvent e) {
        String bookingIdStr = bookingIdField.getText().trim();
        if (bookingIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Booking ID!", "Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int bookingId;
        try {
            bookingId = Integer.parseInt(bookingIdStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Booking ID must be a numeric integer!", "Format Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        loadBookingDetails(bookingId);
    }

    private void loadBookingDetails(int bookingId) {
        Booking booking = bookingController.getBooking(bookingId);
        if (booking == null) {
            JOptionPane.showMessageDialog(this, "No active reservation found with ID #" + bookingId, "Not Found", JOptionPane.WARNING_MESSAGE);
            resetDetails();
            return;
        }

        if ("CANCELLED".equalsIgnoreCase(booking.getTicketStatus())) {
            JOptionPane.showMessageDialog(this, "Cannot checkout: this booking has been cancelled!", "Cancelled Reservation", JOptionPane.ERROR_MESSAGE);
            resetDetails();
            return;
        }

        // Check if already paid
        Payment payment = paymentController.getPaymentByBooking(bookingId);
        if (payment != null) {
            JOptionPane.showMessageDialog(this, "Booking #" + bookingId + " has already been paid!\nTransaction ID: " + payment.getPaymentId(), "Already Paid", JOptionPane.INFORMATION_MESSAGE);
            
            // Show receipt immediately if they want
            int receiptChoice = JOptionPane.showConfirmDialog(this, "Would you like to print/view the transaction receipt?", "View Receipt", JOptionPane.YES_NO_OPTION);
            if (receiptChoice == JOptionPane.YES_OPTION) {
                new ReceiptFrame(payment.getPaymentId()).setVisible(true);
                dispose();
            }
            resetDetails();
            return;
        }

        Passenger passenger = passengerController.getPassenger(booking.getPassengerId());
        Flight flight = flightController.getFlight(booking.getFlightId());

        if (passenger != null && flight != null) {
            selectedBookingId = bookingId;
            passengerNameLbl.setText(passenger.getFullName());
            flightNoLbl.setText(flight.getFlightNumber());
            routeLbl.setText(flight.getDepartureCity() + " -> " + flight.getArrivalCity());
            
            double price = paymentController.calculateTotalPrice(bookingId);
            amountLbl.setText(String.format("$%.2f", price));
            
            processBtn.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Error: Booking data references missing entities!", "Database Error", JOptionPane.ERROR_MESSAGE);
            resetDetails();
        }
    }

    private void resetDetails() {
        selectedBookingId = -1;
        passengerNameLbl.setText("Not Loaded");
        flightNoLbl.setText("Not Loaded");
        routeLbl.setText("Not Loaded");
        amountLbl.setText("$0.00");
        processBtn.setEnabled(false);
    }

    private void handleProcessPayment(ActionEvent e) {
        if (selectedBookingId == -1) return;

        String method = (String) paymentMethodCombo.getSelectedItem();
        String result = paymentController.processPayment(selectedBookingId, method);

        if (result.startsWith("SUCCESS:")) {
            int paymentId = Integer.parseInt(result.split(":")[1]);
            JOptionPane.showMessageDialog(this, "Payment processed successfully!\nTransaction ID: #" + paymentId, "Payment Complete", JOptionPane.INFORMATION_MESSAGE);
            
            // Open Receipt Frame
            new ReceiptFrame(paymentId).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, result, "Payment Processing Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
