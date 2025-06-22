// File: com/gqt/cms/gui/AdminWindow.java
package com.gqt.cms.gui;

import com.gqt.cms.CourseDatabase;
import com.gqt.cms.datamanagement.AdminData;
import com.gqt.cms.datamanagement.ProfessorData;
import com.gqt.cms.datamanagement.StudentData;
import com.gqt.cms.utils.DialogUtil;
import com.gqt.cms.utils.MultiInputDialogUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminWindow extends JFrame {
    private AdminData adminData = new AdminData();
    private StudentData studentData = new StudentData();
    private ProfessorData professorData = new ProfessorData();

    public AdminWindow() {
    	
    	UIManager.put("OptionPane.background", new Color(240, 248, 255));
        UIManager.put("Panel.background", new Color(240, 248, 255));
        UIManager.put("Button.background", new Color(70, 130, 180));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 13));
    	
        setTitle("👤 Admin Panel - GQT CMS");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // === Main Background ===
        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(new Color(240, 248, 255)); // Light blueish

        // === Heading ===
        JLabel heading = new JLabel("Admin Dashboard", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(new Color(30, 60, 120));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        background.add(heading, BorderLayout.NORTH);

        // === Buttons Panel ===
        JPanel btnPanel = new JPanel(new GridLayout(8, 1, 10, 10));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 100, 20, 100));
        btnPanel.setBackground(background.getBackground());

        // === Button List ===
        JButton addCourseBtn = createStyledButton("Add Course");
        JButton addStudentBtn = createStyledButton("Add Student");
        JButton addProfessorBtn = createStyledButton("Add Professor");
        JButton viewCoursesBtn = createStyledButton("View Courses");
        JButton viewStudentsBtn = createStyledButton("View Students");
        JButton viewProfessorsBtn = createStyledButton("View Professors");
        JButton viewEnrollmentsBtn = createStyledButton("View Enrollments");
        JButton logoutBtn = createStyledButton("Logout");

        // === Add to Panel ===
        btnPanel.add(addCourseBtn);
        btnPanel.add(addStudentBtn);
        btnPanel.add(addProfessorBtn);
        btnPanel.add(viewCoursesBtn);
        btnPanel.add(viewStudentsBtn);
        btnPanel.add(viewProfessorsBtn);
        btnPanel.add(viewEnrollmentsBtn);
        btnPanel.add(logoutBtn);

        // === Action Listeners ===
        addCourseBtn.addActionListener(e -> handleAddCourses());
        addStudentBtn.addActionListener(e -> handleAddStudents());
        addProfessorBtn.addActionListener(e -> handleAddProfessors());
        viewStudentsBtn.addActionListener(e -> handleViewStudents());
        viewCoursesBtn.addActionListener(e -> handleViewCourses());
        viewProfessorsBtn.addActionListener(e -> handleViewProfessors());
        viewEnrollmentsBtn.addActionListener(e -> handleViewEnrollments());
        logoutBtn.addActionListener(e -> {
            dispose();
            new MainWindow();
        });

        // === Add Everything to Frame ===
        background.add(btnPanel, BorderLayout.CENTER);
        add(background);
        setVisible(true);
    }
    
    
    
    private void handleAddCourses() {
        Integer count = DialogUtil.askCountInput(this, "Add Courses", "How many courses do you want to add?");
        if (count == null) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.");
            return;
        }

        String[] courses = MultiInputDialogUtil.showInputDialog(this, count, "Enter Course Names", "Course");
        if (courses != null) {
            int added = 0;
            for (String course : courses) {
                if (!course.isEmpty() && CourseDatabase.courseCount < 100) {
                    CourseDatabase.courses[CourseDatabase.courseCount++] = course;
                    added++;
                }
            }
            JOptionPane.showMessageDialog(this, added + " course(s) added successfully.");
        }
    }


    private void handleAddStudents() {
        Integer count = DialogUtil.askCountInput(this, "Add Students", "How many students do you want to add?");
        if (count == null) {
            JOptionPane.showMessageDialog(this, "Invalid number. Try again.");
            return;
        }

        String[] students = MultiInputDialogUtil.showInputDialog(this, count, "Enter Student Names", "Student");
        if (students != null) {
            int added = 0;
            for (String name : students) {
                if (!name.isEmpty() && CourseDatabase.studentCount < 100) {
                    CourseDatabase.students[CourseDatabase.studentCount++] = name;
                    added++;
                }
            }
            JOptionPane.showMessageDialog(this, added + " student(s) added successfully.");
        }
    }

    private void handleAddProfessors() {
        Integer count = DialogUtil.askCountInput(this, "Add Professors", "How many professors do you want to add?");
        if (count == null) {
            JOptionPane.showMessageDialog(this, "Invalid number. Try again.");
            return;
        }

        String[] names = MultiInputDialogUtil.showInputDialog(this, count, "Enter Professor Names", "Professor");
        if (names != null) {
            int added = 0;
            for (String name : names) {
                if (!name.isEmpty() && CourseDatabase.professorCount < 100) {
                    CourseDatabase.professors[CourseDatabase.professorCount++] = name;
                    CourseDatabase.assignedCourses[CourseDatabase.professorCount - 1] = null;
                    added++;
                }
            }
            JOptionPane.showMessageDialog(this, added + " professor(s) added.");
        }
    }
    
    private void handleViewStudents() {
        if (CourseDatabase.studentCount == 0) {
            JOptionPane.showMessageDialog(this,
                    "No students registered yet.",
                    "Student List",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CourseDatabase.studentCount; i++) {
            sb.append(i + 1).append(". ").append(CourseDatabase.students[i]).append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        textArea.setBackground(new Color(245, 250, 255));
        textArea.setForeground(new Color(30, 30, 60));
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 220, 255), 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(380, 250));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Styled panel wrapper
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        panel.add(scrollPane, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, panel, "👥 Registered Students", JOptionPane.PLAIN_MESSAGE);
    }
    private void handleViewProfessors() {
        if (CourseDatabase.professorCount == 0) {
            JOptionPane.showMessageDialog(this,
                    "No professors registered yet.",
                    "Professor List",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CourseDatabase.professorCount; i++) {
            sb.append(i + 1).append(". ").append(CourseDatabase.professors[i]).append("\n");
        }

        showStylishDialog("Registered Professors", sb.toString());
    }

    

    private void handleViewCourses() {
        if (CourseDatabase.courseCount == 0) {
            JOptionPane.showMessageDialog(this,
                    "No courses available yet.",
                    "Course List",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CourseDatabase.courseCount; i++) {
            sb.append(i + 1).append(". ").append(CourseDatabase.courses[i]).append("\n");
        }

        showStylishDialog("📘 Available Courses", sb.toString());
    }

    private void handleViewEnrollments() {
        if (CourseDatabase.enrollmentCount == 0) {
            JOptionPane.showMessageDialog(this,
                    "No enrollments recorded yet.",
                    "Enrollment List",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CourseDatabase.enrollmentCount; i++) {
            sb.append("🧑‍🎓 ").append(CourseDatabase.enrolledStudentNames[i])
              .append(" → ").append(CourseDatabase.enrolledCourseNames[i]).append("\n");
        }

        showStylishDialog("📒 Enrollments", sb.toString());
    }

    private void showStylishDialog(String title, String content) {
        JTextArea textArea = new JTextArea(content);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        textArea.setBackground(new Color(245, 250, 255));
        textArea.setForeground(new Color(30, 30, 60));
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 220, 255), 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(380, 250));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        panel.add(scrollPane, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, panel, title, JOptionPane.PLAIN_MESSAGE);
    }

    
    // ✅ Styled Flat Button with Hover Effect
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(70, 130, 180)); // Steel Blue
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color defaultColor = btn.getBackground();
        Color hoverColor = new Color(60, 110, 160); // Darker blue

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
