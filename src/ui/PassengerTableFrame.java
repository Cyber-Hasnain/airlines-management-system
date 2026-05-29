package ui;

import controller.PassengerController;
import model.Passenger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PassengerTableFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private PassengerController passengerController;
    private JTextField searchField;

    public PassengerTableFrame() {
        passengerController = new PassengerController();

        setTitle("AeroGlobal - Passenger Directory");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIStyle.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Passenger Directory");
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
        searchWindowBtn.addActionListener(e -> new SearchPassengerFrame(this).setVisible(true));
        
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(searchWindowBtn);
        headerPanel.add(searchPanel, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Full Name", "CNIC", "Passport No.", "Gender", "Phone Number", "Email"};
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

        JButton addBtn = UIStyle.createStyledButton("Add Passenger", UIStyle.ACCENT, UIStyle.ACCENT_HOVER);
        JButton updateBtn = UIStyle.createStyledButton("Update Details", UIStyle.BUTTON_PRIMARY, UIStyle.BUTTON_PRIMARY_HOVER);
        JButton deleteBtn = UIStyle.createStyledButton("Delete Passenger", UIStyle.DANGER, UIStyle.DANGER_HOVER);
        JButton refreshBtn = UIStyle.createStyledButton("Refresh Directory", UIStyle.PANEL_DARK, UIStyle.ACCENT);

        addBtn.addActionListener(e -> new AddPassengerFrame(this).setVisible(true));
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
        List<Passenger> list = passengerController.getAllPassengers();
        for (Passenger p : list) {
            tableModel.addRow(new Object[]{
                p.getPassengerId(), p.getFullName(), p.getCnic(), p.getPassportNumber(),
                p.getGender(), p.getPhoneNumber(), p.getEmail()
            });
        }
    }

    public void displaySearchResults(List<Passenger> results) {
        tableModel.setRowCount(0);
        for (Passenger p : results) {
            tableModel.addRow(new Object[]{
                p.getPassengerId(), p.getFullName(), p.getCnic(), p.getPassportNumber(),
                p.getGender(), p.getPhoneNumber(), p.getEmail()
            });
        }
    }

    private void handleSearch() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            refreshData();
        } else {
            List<Passenger> list = passengerController.searchPassengers(keyword);
            displaySearchResults(list);
        }
    }

    private void handleUpdate() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a passenger from the table to update!", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Passenger p = passengerController.getPassenger(id);
        if (p != null) {
            new UpdatePassengerFrame(this, p).setVisible(true);
        }
    }

    private void handleDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a passenger from the table to delete!", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);

        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete passenger '" + name + "'?\nThis action will also delete all associated bookings and payments!", "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            if (passengerController.deletePassenger(id)) {
                JOptionPane.showMessageDialog(this, "Passenger deleted successfully!", "Deleted", JOptionPane.INFORMATION_MESSAGE);
                refreshData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete passenger!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
