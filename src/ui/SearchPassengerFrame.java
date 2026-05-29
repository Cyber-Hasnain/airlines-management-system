package ui;

import controller.PassengerController;
import model.Passenger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class SearchPassengerFrame extends JFrame {
    private JTextField searchField;
    private PassengerController passengerController;
    private PassengerTableFrame parentFrame;

    public SearchPassengerFrame(PassengerTableFrame parentFrame) {
        this.parentFrame = parentFrame;
        passengerController = new PassengerController();

        setTitle("AeroUET - Advanced Passenger Search");
        setSize(400, 220);
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIStyle.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titleLabel = new JLabel("Advanced Passenger Search", SwingConstants.CENTER);
        titleLabel.setFont(UIStyle.FONT_HEADER);
        titleLabel.setForeground(UIStyle.ACCENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0);

        gbc.gridy = 0;
        JLabel searchLbl = new JLabel("Enter search query (Name, CNIC, Passport, Phone, Email)");
        searchLbl.setFont(UIStyle.FONT_SMALL);
        searchLbl.setForeground(UIStyle.TEXT_MUTED);
        contentPanel.add(searchLbl, gbc);

        gbc.gridy = 1;
        searchField = new JTextField();
        UIStyle.styleTextField(searchField);
        contentPanel.add(searchField, gbc);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton searchBtn = UIStyle.createStyledButton("SEARCH", UIStyle.ACCENT, UIStyle.ACCENT_HOVER);
        JButton closeBtn = UIStyle.createStyledButton("CLOSE", UIStyle.PANEL_DARK, UIStyle.DANGER);

        searchBtn.addActionListener(this::handleSearch);
        closeBtn.addActionListener(e -> dispose());

        btnPanel.add(searchBtn);
        btnPanel.add(closeBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void handleSearch(ActionEvent e) {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term!", "Empty Query", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Passenger> results = passengerController.searchPassengers(keyword);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No matching passengers found!", "No Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Found " + results.size() + " passenger(s)!", "Search Results", JOptionPane.INFORMATION_MESSAGE);
            if (parentFrame != null) {
                parentFrame.displaySearchResults(results);
            }
            dispose();
        }
    }
}
