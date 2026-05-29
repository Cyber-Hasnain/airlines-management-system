package ui;

import controller.PassengerController;
import model.Passenger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UpdatePassengerFrame extends JFrame {
    private JTextField nameField, cnicField, passportField, phoneField, emailField;
    private JComboBox<String> genderBox;
    private PassengerController passengerController;
    private PassengerTableFrame parentFrame;
    private Passenger passenger;

    public UpdatePassengerFrame(PassengerTableFrame parentFrame, Passenger passenger) {
        this.parentFrame = parentFrame;
        this.passenger = passenger;
        passengerController = new PassengerController();

        setTitle("AeroUET- Update Passenger Details");
        setSize(400, 520);
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIStyle.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel titleLabel = new JLabel("Update Passenger Details", SwingConstants.CENTER);
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
        gbc.insets = new Insets(8, 0, 4, 0);

        gbc.gridy = 0;
        formPanel.add(createFieldLabel("Full Name"), gbc);
        gbc.gridy = 1;
        nameField = new JTextField();
        UIStyle.styleTextField(nameField);
        nameField.setText(passenger.getFullName());
        formPanel.add(nameField, gbc);

        gbc.gridy = 2;
        formPanel.add(createFieldLabel("CNIC"), gbc);
        gbc.gridy = 3;
        cnicField = new JTextField();
        UIStyle.styleTextField(cnicField);
        cnicField.setText(passenger.getCnic());
        formPanel.add(cnicField, gbc);

        gbc.gridy = 4;
        formPanel.add(createFieldLabel("Passport Number"), gbc);
        gbc.gridy = 5;
        passportField = new JTextField();
        UIStyle.styleTextField(passportField);
        passportField.setText(passenger.getPassportNumber());
        formPanel.add(passportField, gbc);

        gbc.gridy = 6;
        formPanel.add(createFieldLabel("Gender"), gbc);
        gbc.gridy = 7;
        genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderBox.setBackground(new Color(51, 65, 85));
        genderBox.setForeground(UIStyle.TEXT_LIGHT);
        genderBox.setFont(UIStyle.FONT_BODY);
        genderBox.setSelectedItem(passenger.getGender());
        formPanel.add(genderBox, gbc);

        gbc.gridy = 8;
        formPanel.add(createFieldLabel("Phone Number"), gbc);
        gbc.gridy = 9;
        phoneField = new JTextField();
        UIStyle.styleTextField(phoneField);
        phoneField.setText(passenger.getPhoneNumber());
        formPanel.add(phoneField, gbc);

        gbc.gridy = 10;
        formPanel.add(createFieldLabel("Email Address"), gbc);
        gbc.gridy = 11;
        emailField = new JTextField();
        UIStyle.styleTextField(emailField);
        emailField.setText(passenger.getEmail());
        formPanel.add(emailField, gbc);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        UIStyle.styleScrollPane(scrollPane);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

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
        String name = nameField.getText().trim();
        String cnic = cnicField.getText().trim();
        String passport = passportField.getText().trim();
        String gender = (String) genderBox.getSelectedItem();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        String result = passengerController.validateAndUpdate(passenger.getPassengerId(), name, cnic, passport, gender, phone, email);
        if ("SUCCESS".equals(result)) {
            JOptionPane.showMessageDialog(this, "Passenger details updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            if (parentFrame != null) {
                parentFrame.refreshData();
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, result, "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
