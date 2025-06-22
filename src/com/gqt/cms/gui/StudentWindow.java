// File: com/gqt/cms/gui/StudentWindow.java
package com.gqt.cms.gui;

import com.gqt.cms.datamanagement.StudentData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentWindow extends JFrame {

    private StudentData studentData = new StudentData();

    public StudentWindow() {
    	UIManager.put("OptionPane.background", new Color(240, 248, 255));
        UIManager.put("Panel.background", new Color(240, 248, 255));
        UIManager.put("Button.background", new Color(70, 130, 180));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 13));
    	
        setTitle("🎓 Student Panel - GQT CMS");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // === Background Panel ===
        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(new Color(240, 248, 255)); // Light blue

        // === Heading ===
        JLabel heading = new JLabel("Student Dashboard", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(new Color(25, 50, 130));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        background.add(heading, BorderLayout.NORTH);

        // === Buttons Panel ===
        JPanel btnPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        btnPanel.setBackground(background.getBackground());
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 30, 100));

        JButton registerBtn = createStyledButton("Register as Student");
        JButton viewCoursesBtn = createStyledButton("View Available Courses");
        JButton enrollBtn = createStyledButton("Enroll in a Course");
        JButton backBtn = createStyledButton("Back to Main Menu");

        btnPanel.add(registerBtn);
        btnPanel.add(viewCoursesBtn);
        btnPanel.add(enrollBtn);
        btnPanel.add(backBtn);

        background.add(btnPanel, BorderLayout.CENTER);
        add(background);

        // === Action Listeners ===
        registerBtn.addActionListener(e -> showStudentRegistrationDialog());
        viewCoursesBtn.addActionListener(e -> showStyledCourseList());
        enrollBtn.addActionListener(e -> showStyledEnrollmentDialog());
        backBtn.addActionListener(e -> {
            dispose();
            new MainWindow();
        });

        setVisible(true);
    }

    private void showStudentRegistrationDialog() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Enter your name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField nameField = new JTextField(15);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setBackground(new Color(245, 250, 255));

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "🎓 Student Registration",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            if (!name.isEmpty() && com.gqt.cms.CourseDatabase.studentCount < 100) {
                com.gqt.cms.CourseDatabase.students[com.gqt.cms.CourseDatabase.studentCount++] = name;
                JOptionPane.showMessageDialog(
                        this,
                        "Welcome, " + name + "! Registration successful.",
                        "✅ Registered", JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed or limit exceeded.");
            }
        }
    }

    private void showStyledCourseList() {
        if (com.gqt.cms.CourseDatabase.courseCount == 0) {
            JOptionPane.showMessageDialog(this, "No courses available yet.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < com.gqt.cms.CourseDatabase.courseCount; i++) {
            sb.append(i + 1).append(". ").append(com.gqt.cms.CourseDatabase.courses[i]).append("\n");
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        area.setBackground(new Color(245, 250, 255));
        area.setForeground(new Color(30, 30, 60));
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 240), 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(380, 220));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.add(scrollPane);

        JOptionPane.showMessageDialog(this, panel, "📘 Available Courses", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void showStyledEnrollmentDialog() {
        if (com.gqt.cms.CourseDatabase.studentCount == 0) {
            JOptionPane.showMessageDialog(this, "No students registered. Please register first.");
            return;
        }

        if (com.gqt.cms.CourseDatabase.courseCount == 0) {
            JOptionPane.showMessageDialog(this, "No courses available to enroll.");
            return;
        }

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Student Name Input
        JLabel nameLabel = new JLabel("Enter your name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField nameField = new JTextField(15);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setBackground(new Color(245, 250, 255));

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Course Dropdown
        JLabel courseLabel = new JLabel("Select course:");
        courseLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JComboBox<String> courseBox = new JComboBox<>(com.gqt.cms.CourseDatabase.courses);
        courseBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        courseBox.setBackground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(courseLabel, gbc);
        gbc.gridx = 1;
        panel.add(courseBox, gbc);

        // Show dialog
        int result = JOptionPane.showConfirmDialog(
                this, panel, "📚 Enroll in a Course",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String student = nameField.getText().trim();
            String course = (String) courseBox.getSelectedItem();

            if (student.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty.");
                return;
            }

            com.gqt.cms.CourseDatabase.enrolledStudentNames[com.gqt.cms.CourseDatabase.enrollmentCount] = student;
            com.gqt.cms.CourseDatabase.enrolledCourseNames[com.gqt.cms.CourseDatabase.enrollmentCount] = course;
            com.gqt.cms.CourseDatabase.enrollmentCount++;

            JOptionPane.showMessageDialog(this, student + ", you enrolled in " + course + ".");
        }
    }

    
    // ✅ Reusable styled flat button
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
