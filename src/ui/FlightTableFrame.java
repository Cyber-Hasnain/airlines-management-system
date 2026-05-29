package ui;

import controller.FlightController;
import model.Flight;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FlightTableFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private FlightController flightController;
    private JTextField searchField;

    public FlightTableFrame() {
        flightController = new FlightController();

        setTitle("AeroUET - Scheduled Flights Directory");
        setSize(950, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIStyle.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Scheduled Flights Directory");
        titleLabel.setFont(UIStyle.FONT_TITLE);
        titleLabel.setForeground(UIStyle.TEXT_LIGHT);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);
        searchField = new JTextField(15);
        UIStyle.styleTextField(searchField);
        JButton searchBtn = UIStyle.createStyledButton("Search", UIStyle.BUTTON_PRIMARY, UIStyle.BUTTON_PRIMARY_HOVER);
        searchBtn.addActionListener(e -> handleSearch());
        JButton searchWindowBtn = UIStyle.createStyledButton("Advanced Search", UIStyle.PANEL_DARK, UIStyle.ACCENT);
        searchWindowBtn.addActionListener(e -> new SearchFlightFrame(this).setVisible(true));
        
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(searchWindowBtn);
        headerPanel.add(searchPanel, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Flight No.", "Departure City", "Arrival City", "Dept. Time", "Arr. Time", "Date", "Total Seats", "Avail. Seats", "Price"};
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
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        footerPanel.setOpaque(false);

        JButton addBtn = UIStyle.createStyledButton("Add Flight", UIStyle.ACCENT, UIStyle.ACCENT_HOVER);
        JButton updateBtn = UIStyle.createStyledButton("Update Flight", UIStyle.BUTTON_PRIMARY, UIStyle.BUTTON_PRIMARY_HOVER);
        JButton deleteBtn = UIStyle.createStyledButton("Delete Flight", UIStyle.DANGER, UIStyle.DANGER_HOVER);
        JButton refreshBtn = UIStyle.createStyledButton("Refresh Schedule", UIStyle.PANEL_DARK, UIStyle.ACCENT);

        addBtn.addActionListener(e -> new AddFlightFrame(this).setVisible(true));
        updateBtn.addActionListener(e -> handleUpdate());
        deleteBtn.addActionListener(e -> handleDelete());
        refreshBtn.addActionListener(e -> refreshData());

        footerPanel.add(addBtn);
        footerPanel.add(updateBtn);
        footerPanel.add(deleteBtn);
        footerPanel.add(refreshBtn);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        refreshData();
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        List<Flight> list = flightController.getAllFlights();
        for (Flight f : list) {
            tableModel.addRow(new Object[]{
                f.getFlightId(), f.getFlightNumber(), f.getDepartureCity(), f.getArrivalCity(),
                f.getDepartureTime(), f.getArrivalTime(), f.getFlightDate().toString(),
                f.getTotalSeats(), f.getAvailableSeats(), String.format("$%.2f", f.getTicketPrice())
            });
        }
    }

    public void displaySearchResults(List<Flight> results) {
        tableModel.setRowCount(0);
        for (Flight f : results) {
            tableModel.addRow(new Object[]{
                f.getFlightId(), f.getFlightNumber(), f.getDepartureCity(), f.getArrivalCity(),
                f.getDepartureTime(), f.getArrivalTime(), f.getFlightDate().toString(),
                f.getTotalSeats(), f.getAvailableSeats(), String.format("$%.2f", f.getTicketPrice())
            });
        }
    }

    private void handleSearch() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            refreshData();
        } else {
            List<Flight> list = flightController.searchFlights(keyword);
            displaySearchResults(list);
        }
    }

    private void handleUpdate() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a flight from the table to update!", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Flight f = flightController.getFlight(id);
        if (f != null) {
            new UpdateFlightFrame(this, f).setVisible(true);
        }
    }

    private void handleDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a flight from the table to delete!", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String number = (String) tableModel.getValueAt(selectedRow, 1);

        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete flight '" + number + "'?\nThis action will also delete all associated bookings, tickets, and payments!", "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            if (flightController.deleteFlight(id)) {
                JOptionPane.showMessageDialog(this, "Flight deleted successfully!", "Deleted", JOptionPane.INFORMATION_MESSAGE);
                refreshData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete flight!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
