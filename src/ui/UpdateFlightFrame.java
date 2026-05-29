package ui;

import controller.FlightController;
import model.Flight;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UpdateFlightFrame extends JFrame {
    private JTextField flightNumField, deptCityField, arrCityField, deptTimeField, arrTimeField, dateField, totalSeatsField, availSeatsField, priceField;
    private FlightController flightController;
    private FlightTableFrame parentFrame;
    private Flight flight;

    public UpdateFlightFrame(FlightTableFrame parentFrame, Flight flight) {
        this.parentFrame = parentFrame;
        this.flight = flight;
        flightController = new FlightController();

        setTitle("AeroUET - Update Flight Details");
        setSize(420, 590);
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIStyle.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JLabel titleLabel = new JLabel("Update Flight Details", SwingConstants.CENTER);
        titleLabel.setFont(UIStyle.FONT_TITLE);
        titleLabel.setForeground(UIStyle.ACCENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 2, 0);

        gbc.gridy = 0;
        formPanel.add(createFieldLabel("Flight Number"), gbc);
        gbc.gridy = 1;
        flightNumField = new JTextField();
        UIStyle.styleTextField(flightNumField);
        flightNumField.setText(flight.getFlightNumber());
        formPanel.add(flightNumField, gbc);

        gbc.gridy = 2;
        formPanel.add(createFieldLabel("Departure City"), gbc);
        gbc.gridy = 3;
        deptCityField = new JTextField();
        UIStyle.styleTextField(deptCityField);
        deptCityField.setText(flight.getDepartureCity());
        formPanel.add(deptCityField, gbc);

        gbc.gridy = 4;
        formPanel.add(createFieldLabel("Arrival City"), gbc);
        gbc.gridy = 5;
        arrCityField = new JTextField();
        UIStyle.styleTextField(arrCityField);
        arrCityField.setText(flight.getArrivalCity());
        formPanel.add(arrCityField, gbc);

        gbc.gridy = 6;
        formPanel.add(createFieldLabel("Departure Time (HH:MM)"), gbc);
        gbc.gridy = 7;
        deptTimeField = new JTextField();
        UIStyle.styleTextField(deptTimeField);
        deptTimeField.setText(flight.getDepartureTime());
        formPanel.add(deptTimeField, gbc);

        gbc.gridy = 8;
        formPanel.add(createFieldLabel("Arrival Time (HH:MM)"), gbc);
        gbc.gridy = 9;
        arrTimeField = new JTextField();
        UIStyle.styleTextField(arrTimeField);
        arrTimeField.setText(flight.getArrivalTime());
        formPanel.add(arrTimeField, gbc);

        gbc.gridy = 10;
        formPanel.add(createFieldLabel("Flight Date (YYYY-MM-DD)"), gbc);
        gbc.gridy = 11;
        dateField = new JTextField();
        UIStyle.styleTextField(dateField);
        dateField.setText(flight.getFlightDate().toString());
        formPanel.add(dateField, gbc);

        gbc.gridy = 12;
        formPanel.add(createFieldLabel("Total Seats"), gbc);
        gbc.gridy = 13;
        totalSeatsField = new JTextField();
        UIStyle.styleTextField(totalSeatsField);
        totalSeatsField.setText(String.valueOf(flight.getTotalSeats()));
        formPanel.add(totalSeatsField, gbc);

        gbc.gridy = 14;
        formPanel.add(createFieldLabel("Available Seats"), gbc);
        gbc.gridy = 15;
        availSeatsField = new JTextField();
        UIStyle.styleTextField(availSeatsField);
        availSeatsField.setText(String.valueOf(flight.getAvailableSeats()));
        formPanel.add(availSeatsField, gbc);

        gbc.gridy = 16;
        formPanel.add(createFieldLabel("Ticket Price ($)"), gbc);
        gbc.gridy = 17;
        priceField = new JTextField();
        UIStyle.styleTextField(priceField);
        priceField.setText(String.valueOf(flight.getTicketPrice()));
        formPanel.add(priceField, gbc);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        UIStyle.styleScrollPane(scrollPane);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton updateBtn = UIStyle.createStyledButton("UPDATE", UIStyle.ACCENT, UIStyle.ACCENT_HOVER);
        JButton cancelBtn = UIStyle.createStyledButton("CANCEL", UIStyle.PANEL_DARK, UIStyle.DANGER);

        updateBtn.addActionListener(this::handleUpdate);
        cancelBtn.addActionListener(e -> dispose());

        btnPanel.add(updateBtn);
        btnPanel.add(cancelBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JLabel createFieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(UIStyle.FONT_SMALL);
        lbl.setForeground(UIStyle.TEXT_MUTED);
        return lbl;
    }

    private void handleUpdate(ActionEvent e) {
        String num = flightNumField.getText().trim();
        String dept = deptCityField.getText().trim();
        String arr = arrCityField.getText().trim();
        String deptT = deptTimeField.getText().trim();
        String arrT = arrTimeField.getText().trim();
        String date = dateField.getText().trim();
        String totalSeats = totalSeatsField.getText().trim();
        String availSeats = availSeatsField.getText().trim();
        String price = priceField.getText().trim();

        String result = flightController.validateAndUpdate(flight.getFlightId(), num, dept, arr, deptT, arrT, date, totalSeats, availSeats, price);
        if ("SUCCESS".equals(result)) {
            JOptionPane.showMessageDialog(this, "Flight details updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            if (parentFrame != null) {
                parentFrame.refreshData();
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, result, "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
