package ui;

import controller.LoginController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private LoginController loginController;

    public LoginFrame() {
        loginController = new LoginController();

        setTitle("AeroUET - Admin Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIStyle.BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("AeroUET", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(UIStyle.ACCENT);
        JLabel subLabel = new JLabel("Admin Portal Login", SwingConstants.CENTER);
        subLabel.setFont(UIStyle.FONT_BODY);
        subLabel.setForeground(UIStyle.TEXT_MUTED);
        headerPanel.add(titleLabel);
        headerPanel.add(subLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 5, 0);

        gbc.gridy = 0;
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(UIStyle.FONT_HEADER);
        userLabel.setForeground(UIStyle.TEXT_LIGHT);
        formPanel.add(userLabel, gbc);

        gbc.gridy = 1;
        userField = new JTextField(20);
        UIStyle.styleTextField(userField);
        userField.setText("admin"); 
        formPanel.add(userField, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(15, 0, 5, 0);
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(UIStyle.FONT_HEADER);
        passLabel.setForeground(UIStyle.TEXT_LIGHT);
        formPanel.add(passLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(5, 0, 5, 0);
        passField = new JPasswordField(20);
        UIStyle.styleTextField(passField);
        passField.setText("admin123"); 
        formPanel.add(passField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JButton loginBtn = UIStyle.createStyledButton("LOG IN", UIStyle.ACCENT, UIStyle.ACCENT_HOVER);
        loginBtn.addActionListener(this::handleLogin);
        footerPanel.add(loginBtn, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void handleLogin(ActionEvent e) {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password!", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (loginController.login(username, password)) {
            JOptionPane.showMessageDialog(this, "Welcome back, " + username + "!", "Login Success", JOptionPane.INFORMATION_MESSAGE);
            new DashboardFrame().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials! Please try again.", "Authentication Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
