package ui;

import controller.BookingController;
import controller.FlightController;
import controller.PassengerController;
import model.Booking;
import model.Flight;
import model.Passenger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class BookTicketFrame extends JFrame {
    private JComboBox<Passenger> passengerCombo;
    private JComboBox<Flight> flightCombo;
    private JTextField seatField;
    private JTable table;
    private DefaultTableModel tableModel;
    private BookingController bookingController;
    private PassengerController passengerController;
    private FlightController flightController;
    private JTextField searchField;

    public BookTicketFrame() {
        bookingController = new BookingController();
        passengerController = new PassengerController();
        flightController = new FlightController();

        setTitle("AeroUET - Ticket Booking System");
        setSize(1100, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 0));
        mainPanel.setBackground(UIStyle.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // LEFT PANEL: Booking Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(UIStyle.PANEL_DARK);
        formPanel.setPreferredSize(new Dimension(380, 600));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(51, 65, 85), 1),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(8, 0, 4, 0);

        gbc.gridy = 0;
        JLabel formTitle = new JLabel("Create Booking");
        formTitle.setFont(UIStyle.FONT_TITLE);
        formTitle.setForeground(UIStyle.ACCENT);
        formTitle.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(formTitle, gbc);

        gbc.gridy = 1;
        formPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);

        gbc.gridy = 2;
        formPanel.add(createFieldLabel("Select Passenger"), gbc);
        gbc.gridy = 3;
        passengerCombo = new JComboBox<>();
        passengerCombo.setBackground(new Color(51, 65, 85));
        passengerCombo.setForeground(UIStyle.TEXT_LIGHT);
        passengerCombo.setFont(UIStyle.FONT_BODY);
        formPanel.add(passengerCombo, gbc);

        gbc.gridy = 4;
        formPanel.add(createFieldLabel("Select Scheduled Flight"), gbc);
        gbc.gridy = 5;
        flightCombo = new JComboBox<>();
        flightCombo.setBackground(new Color(51, 65, 85));
        flightCombo.setForeground(UIStyle.TEXT_LIGHT);
        flightCombo.setFont(UIStyle.FONT_BODY);
        formPanel.add(flightCombo, gbc);

        gbc.gridy = 6;
        formPanel.add(createFieldLabel("Seat Number Assignment"), gbc);
        gbc.gridy = 7;
        seatField = new JTextField();
        UIStyle.styleTextField(seatField);
        seatField.setToolTipText("Enter a positive integer seat number");
        formPanel.add(seatField, gbc);

        gbc.gridy = 8;
        gbc.insets = new Insets(25, 0, 0, 0);
        JButton bookBtn = UIStyle.createStyledButton("BOOK FLIGHT SEAT", UIStyle.ACCENT, UIStyle.ACCENT_HOVER);
        bookBtn.addActionListener(this::handleBookTicket);
        formPanel.add(bookBtn, gbc);

        mainPanel.add(formPanel, BorderLayout.WEST);

        // CENTER PANEL: Table & Search & Footer Controls
        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setOpaque(false);

        // Header Panel (Search)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel tableTitle = new JLabel("Reservation Logs Directory");
        tableTitle.setFont(UIStyle.FONT_TITLE);
        tableTitle.setForeground(UIStyle.TEXT_LIGHT);
        headerPanel.add(tableTitle, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);
        searchField = new JTextField(15);
        UIStyle.styleTextField(searchField);
        JButton searchBtn = UIStyle.createStyledButton("Search", UIStyle.BUTTON_PRIMARY, UIStyle.BUTTON_PRIMARY_HOVER);
        searchBtn.addActionListener(e -> handleSearch());
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        centerPanel.add(headerPanel, BorderLayout.NORTH);

        // Bookings Table
        String[] columns = {"Booking ID", "Passenger Name", "Flight No.", "Seat No.", "Booking Date", "Ticket Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        UIStyle.styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(UIStyle.BG_DARK);
        scrollPane.getViewport().setBackground(UIStyle.BG_DARK);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(51, 65, 85)));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer Operations
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        footerPanel.setOpaque(false);

        JButton viewPassBtn = UIStyle.createStyledButton("Boarding Pass", UIStyle.ACCENT, UIStyle.ACCENT_HOVER);
        JButton payBtn = UIStyle.createStyledButton("Pay Ticket", new Color(245, 158, 11), new Color(217, 119, 6)); // Orange Accents
        JButton cancelBtn = UIStyle.createStyledButton("Cancel Booking", UIStyle.DANGER, UIStyle.DANGER_HOVER);
        JButton refreshBtn = UIStyle.createStyledButton("Refresh Logs", UIStyle.PANEL_DARK, UIStyle.ACCENT);

        viewPassBtn.addActionListener(e -> handleViewBoardingPass());
        payBtn.addActionListener(e -> handlePayTicket());
        cancelBtn.addActionListener(e -> handleCancelBooking());
        refreshBtn.addActionListener(e -> refreshData());

        footerPanel.add(viewPassBtn);
        footerPanel.add(payBtn);
        footerPanel.add(cancelBtn);
        footerPanel.add(refreshBtn);
        centerPanel.add(footerPanel, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);

        // Initial Load
        loadSelectableEntities();
        refreshData();
    }

    private JLabel createFieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(UIStyle.FONT_SMALL);
        lbl.setForeground(UIStyle.TEXT_MUTED);
        lbl.setBorder(BorderFactory.createEmptyBorder(8, 0, 2, 0));
        return lbl;
    }

    private void loadSelectableEntities() {
        passengerCombo.removeAllItems();
        List<Passenger> passengers = passengerController.getAllPassengers();
        for (Passenger p : passengers) {
            passengerCombo.addItem(p);
        }

        flightCombo.removeAllItems();
        List<Flight> flights = flightController.getAllFlights();
        for (Flight f : flights) {
            if (f.getAvailableSeats() > 0) {
                flightCombo.addItem(f);
            }
        }
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        List<Booking> list = bookingController.getAllBookings();
        populateTable(list);
    }

    private void populateTable(List<Booking> list) {
        for (Booking b : list) {
            Passenger p = passengerController.getPassenger(b.getPassengerId());
            Flight f = flightController.getFlight(b.getFlightId());

            String passengerName = p != null ? p.getFullName() : "Unknown ID: " + b.getPassengerId();
            String flightNo = f != null ? f.getFlightNumber() : "Unknown ID: " + b.getFlightId();

            tableModel.addRow(new Object[]{
                b.getBookingId(),
                passengerName,
                flightNo,
                b.getSeatNumber(),
                b.getBookingDate() != null ? b.getBookingDate().toString() : "Pending",
                b.getTicketStatus()
            });
        }
    }

    private void handleSearch() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            refreshData();
        } else {
            tableModel.setRowCount(0);
            List<Booking> list = bookingController.searchBookings(keyword);
            populateTable(list);
        }
    }

    private void handleBookTicket(ActionEvent e) {
        Passenger passenger = (Passenger) passengerCombo.getSelectedItem();
        Flight flight = (Flight) flightCombo.getSelectedItem();
        String seatStr = seatField.getText().trim();

        if (passenger == null) {
            JOptionPane.showMessageDialog(this, "Please select or register a passenger first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (flight == null) {
            JOptionPane.showMessageDialog(this, "No flights available!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (seatStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please assign a seat number!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int seatNumber;
        try {
            seatNumber = Integer.parseInt(seatStr);
            if (seatNumber <= 0 || seatNumber > flight.getTotalSeats()) {
                JOptionPane.showMessageDialog(this, "Seat must be between 1 and " + flight.getTotalSeats(), "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Seat number must be a valid integer!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String result = bookingController.bookTicket(passenger.getPassengerId(), flight.getFlightId(), seatNumber);
        if (result.startsWith("SUCCESS:")) {
            int bookingId = Integer.parseInt(result.split(":")[1]);
            JOptionPane.showMessageDialog(this, "Booking Successful! Reservation ID: " + bookingId, "Success", JOptionPane.INFORMATION_MESSAGE);
            seatField.setText("");
            refreshData();
            loadSelectableEntities(); // Update available seats in combos

            // Ask if they want to pay now
            int payChoice = JOptionPane.showConfirmDialog(this, "Would you like to process payment and issue the digital boarding pass immediately?", "Process Payment", JOptionPane.YES_NO_OPTION);
            if (payChoice == JOptionPane.YES_OPTION) {
                new PaymentFrame(bookingId).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, result, "Booking Refused", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCancelBooking() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking row from the table!", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int bookingId = (int) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 5);

        if ("CANCELLED".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "This booking has already been cancelled!", "Already Cancelled", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel booking reservation #" + bookingId + "?\nThis will re-open the seat availability.", "Confirm Cancellation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            if (bookingController.cancelTicket(bookingId)) {
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshData();
                loadSelectableEntities(); // update flight availability list
            } else {
                JOptionPane.showMessageDialog(this, "Failed to cancel booking reservation!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleViewBoardingPass() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to preview its Boarding Pass!", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int bookingId = (int) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 5);

        if ("CANCELLED".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "Cannot print a boarding pass for a cancelled reservation!", "Booking Cancelled", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Preview Boarding pass
        new TicketPreviewFrame(bookingId).setVisible(true);
    }

    private void handlePayTicket() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to process payment!", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int bookingId = (int) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 5);

        if ("CANCELLED".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "Cannot process payment for a cancelled booking!", "Booking Cancelled", JOptionPane.ERROR_MESSAGE);
            return;
        }

        new PaymentFrame(bookingId).setVisible(true);
    }
}
