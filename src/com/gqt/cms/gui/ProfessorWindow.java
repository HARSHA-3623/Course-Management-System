// File: com/gqt/cms/gui/ProfessorWindow.java
package com.gqt.cms.gui;

import com.gqt.cms.datamanagement.ProfessorData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProfessorWindow extends JFrame {

    private ProfessorData professorData = new ProfessorData();

    public ProfessorWindow() {
    	UIManager.put("OptionPane.background", new Color(240, 248, 255));
        UIManager.put("Panel.background", new Color(240, 248, 255));
        UIManager.put("Button.background", new Color(70, 130, 180));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 13));
    	
        setTitle("📘 Professor Panel - GQT CMS");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // === Background Panel ===
        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(new Color(240, 248, 255)); // Light bluish

        // === Heading ===
        JLabel heading = new JLabel("Professor Dashboard", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(new Color(25, 60, 120));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        background.add(heading, BorderLayout.NORTH);

        // === Button Panel ===
        JPanel btnPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        btnPanel.setBackground(background.getBackground());
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 30, 100));

        JButton registerBtn = createStyledButton("Register as Professor");
        JButton assignCourseBtn = createStyledButton("Assign Course");
        JButton viewEnrolledBtn = createStyledButton("View Enrolled Students");
        JButton backBtn = createStyledButton("Back to Main Menu");

        btnPanel.add(registerBtn);
        btnPanel.add(assignCourseBtn);
        btnPanel.add(viewEnrolledBtn);
        btnPanel.add(backBtn);

        background.add(btnPanel, BorderLayout.CENTER);
        add(background);

        // === Action Listeners ===
        registerBtn.addActionListener(e -> showStyledRegisterDialog());
        
        assignCourseBtn.addActionListener(e -> showStyledCourseAssignDialog());
        
        viewEnrolledBtn.addActionListener(e -> showStyledViewEnrolledDialog());

        backBtn.addActionListener(e -> {
            dispose();
            new MainWindow();
        });

        setVisible(true);
    }

    private void showStyledRegisterDialog() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("Enter Professor Name:");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField textField = new JTextField(15);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBackground(new Color(245, 250, 255));

        gbc.gridx = 0;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(textField, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel, "👤 Register Professor",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = textField.getText().trim();
            if (!name.isEmpty()) {
                com.gqt.cms.CourseDatabase.professors[com.gqt.cms.CourseDatabase.professorCount++] = name;
                JOptionPane.showMessageDialog(this, "✅ Professor " + name + " registered.");
            }
        }
    }


    private void showStyledCourseAssignDialog() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("Enter your name:");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField nameField = new JTextField(15);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setBackground(new Color(245, 250, 255));

        gbc.gridx = 0;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel, "📘 Assign Course",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                professorData.assignCourseToProfessor(name);
            }
        }
    }

    
    private void showStyledViewEnrolledDialog() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("Enter your name:");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField nameField = new JTextField(15);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setBackground(new Color(245, 250, 255));

        gbc.gridx = 0;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel, "👥 View Enrolled Students",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                professorData.viewStudentsByCourse(name);
            }
        }
    }

    // ✅ Shared styled button method
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color defaultColor = btn.getBackground();
        Color hoverColor = new Color(60, 110, 160);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hoverColor);
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(defaultColor);
            }
        });

        return btn;
    }
}
