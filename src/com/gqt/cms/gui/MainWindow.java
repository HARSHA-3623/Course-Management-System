// File: com/gqt/cms/gui/MainWindow.java
package com.gqt.cms.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {

    public MainWindow() {
    	UIManager.put("OptionPane.background", new Color(240, 248, 255));
        UIManager.put("Panel.background", new Color(240, 248, 255));
        UIManager.put("Button.background", new Color(70, 130, 180));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 13));
    	
        setTitle("GQT Course Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);

        // === Background Panel ===
        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(new Color(230, 240, 255)); // Light bluish background

        // === Welcome Message ===
        JLabel welcome = new JLabel("Welcome to GQT Course Management System", JLabel.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcome.setForeground(new Color(30, 60, 120));
        welcome.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        background.add(welcome, BorderLayout.NORTH);

        // === Button Panel ===
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        buttonPanel.setBackground(new Color(230, 240, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 30, 100));

        // === Buttons with Hover Effect ===
        JButton adminBtn = createStyledButton("Admin");
        JButton studentBtn = createStyledButton("Student");
        JButton professorBtn = createStyledButton("Professor");
        JButton exitBtn = createStyledButton("Exit");

        // Add buttons
        buttonPanel.add(adminBtn);
        buttonPanel.add(studentBtn);
        buttonPanel.add(professorBtn);
        buttonPanel.add(exitBtn);

        background.add(buttonPanel, BorderLayout.CENTER);

        // === Add background panel to frame ===
        add(background);

        // === Action Listeners ===
        adminBtn.addActionListener(e -> {
            dispose();
            showStyledAdminLogin();
        });

        studentBtn.addActionListener(e -> {
            dispose();
            new StudentWindow();
        });

        professorBtn.addActionListener(e -> {
            dispose();
            new ProfessorWindow();
        });

        exitBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void showStyledAdminLogin() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 250, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Admin Login");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(30, 60, 120));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField userField = new JTextField(15);
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userField.setBackground(new Color(255, 255, 255));

        panel.add(userLabel, gbc);
        gbc.gridx = 1;
        panel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JPasswordField passField = new JPasswordField(15);
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passField.setBackground(new Color(255, 255, 255));

        panel.add(passLabel, gbc);
        gbc.gridx = 1;
        panel.add(passField, gbc);
        setLocationRelativeTo(null);
        
        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Admin Authentication",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());

            if ("admin".equalsIgnoreCase(username) && "admin123".equals(password)) {
                new AdminWindow();
            } else {
                JOptionPane.showMessageDialog(this,
                        "❌ Invalid credentials! Try again.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                showStyledAdminLogin(); // Retry
            }
        } else {
            new MainWindow(); // Return to role menu
        }
    }

    
    // ✅ Reusable method for styled buttons with hover
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setBorder(BorderFactory.createLineBorder(new Color(50, 100, 160), 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        Color defaultColor = btn.getBackground();
        Color hoverColor = new Color(100, 149, 237); // Cornflower Blue

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(hoverColor);
            }

            public void mouseExited(MouseEvent evt) {
                btn.setBackground(defaultColor);
            }
        });

        return btn;
    }
}
