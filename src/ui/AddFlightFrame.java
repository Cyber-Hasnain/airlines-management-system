package ui;

import controller.FlightController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddFlightFrame extends JFrame {
    private JTextField flightNumField, deptCityField, arrCityField, deptTimeField, arrTimeField, dateField, seatsField, priceField;
    private FlightController flightController;
    private FlightTableFrame parentFrame;

    public AddFlightFrame(FlightTableFrame parentFrame) {
        this.parentFrame = parentFrame;
        flightController = new FlightController();

        setTitle("AeroUET - Add Flight");
        setSize(420, 580);
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIStyle.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel titleLabel = new JLabel("Add New Scheduled Flight", SwingConstants.CENTER);
        titleLabel.setFont(UIStyle.FONT_TITLE);
        titleLabel.setForeground(UIStyle.ACCENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(6, 0, 3, 0);

        gbc.gridy = 0;
        formPanel.add(createFieldLabel("Flight Number (e.g. PK-301)"), gbc);
        gbc.gridy = 1;
        flightNumField = new JTextField();
        UIStyle.styleTextField(flightNumField);
        formPanel.add(flightNumField, gbc);

        gbc.gridy = 2;
        formPanel.add(createFieldLabel("Departure City"), gbc);
        gbc.gridy = 3;
        deptCityField = new JTextField();
        UIStyle.styleTextField(deptCityField);
        formPanel.add(deptCityField, gbc);

        gbc.gridy = 4;
        formPanel.add(createFieldLabel("Arrival City"), gbc);
        gbc.gridy = 5;
        arrCityField = new JTextField();
        UIStyle.styleTextField(arrCityField);
        formPanel.add(arrCityField, gbc);

        gbc.gridy = 6;
        formPanel.add(createFieldLabel("Departure Time (HH:MM 24-hr)"), gbc);
        gbc.gridy = 7;
        deptTimeField = new JTextField();
        UIStyle.styleTextField(deptTimeField);
        deptTimeField.setText("12:00");
        formPanel.add(deptTimeField, gbc);

        gbc.gridy = 8;
        formPanel.add(createFieldLabel("Arrival Time (HH:MM 24-hr)"), gbc);
        gbc.gridy = 9;
        arrTimeField = new JTextField();
        UIStyle.styleTextField(arrTimeField);
        arrTimeField.setText("14:30");
        formPanel.add(arrTimeField, gbc);

        gbc.gridy = 10;
        formPanel.add(createFieldLabel("Flight Date (YYYY-MM-DD)"), gbc);
        gbc.gridy = 11;
        dateField = new JTextField();
        UIStyle.styleTextField(dateField);
        dateField.setText("2026-06-01");
        formPanel.add(dateField, gbc);

        gbc.gridy = 12;
        formPanel.add(createFieldLabel("Total Seats"), gbc);
        gbc.gridy = 13;
        seatsField = new JTextField();
        UIStyle.styleTextField(seatsField);
        seatsField.setText("150");
        formPanel.add(seatsField, gbc);

        gbc.gridy = 14;
        formPanel.add(createFieldLabel("Ticket Price ($)"), gbc);
        gbc.gridy = 15;
        priceField = new JTextField();
        UIStyle.styleTextField(priceField);
        priceField.setText("250.00");
        formPanel.add(priceField, gbc);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        UIStyle.styleScrollPane(scrollPane);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton saveBtn = UIStyle.createStyledButton("SAVE", UIStyle.ACCENT, UIStyle.ACCENT_HOVER);
        JButton cancelBtn = UIStyle.createStyledButton("CANCEL", UIStyle.PANEL_DARK, UIStyle.DANGER);

        saveBtn.addActionListener(this::handleSave);
        cancelBtn.addActionListener(e -> dispose());

        btnPanel.add(saveBtn);
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

    private void handleSave(ActionEvent e) {
        String num = flightNumField.getText().trim();
        String dept = deptCityField.getText().trim();
        String arr = arrCityField.getText().trim();
        String deptT = deptTimeField.getText().trim();
        String arrT = arrTimeField.getText().trim();
        String date = dateField.getText().trim();
        String seats = seatsField.getText().trim();
        String price = priceField.getText().trim();

        String result = flightController.validateAndAdd(num, dept, arr, deptT, arrT, date, seats, price);
        if ("SUCCESS".equals(result)) {
            JOptionPane.showMessageDialog(this, "Flight scheduled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            if (parentFrame != null) {
                parentFrame.refreshData();
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, result, "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
