// File: com/gqt/cms/datamanagement/StudentData.java
package com.gqt.cms.datamanagement;

import com.gqt.cms.CourseDatabase;

import javax.swing.*;
import java.awt.*;

public class StudentData {

    private String loggedInStudent = null; // Track currently registered student

    public void StudentMenu() {
        while (true) {
            String[] options = { "Register", "View Courses", "Enroll in Course", "Logout" };
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Student Menu - Choose an option",
                    "Student Panel",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 0) {
                RegisterStudents(false);
            } else if (choice == 1) {
                displayCourses();
            } else if (choice == 2) {
                EnrollCourse();
            } else if (choice == 3 || choice == JOptionPane.CLOSED_OPTION) {
                return; // Exit to MainWindow
            }
        }
    }

    public void RegisterStudents(boolean isAdmin) {
        if (isAdmin) {
            String input = JOptionPane.showInputDialog("How many students do you want to add?");
            if (input == null) return;

            int num;
            try {
                num = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "❌ Invalid number!");
                return;
            }

            for (int i = 0; i < num; i++) {
                if (CourseDatabase.studentCount >= CourseDatabase.students.length) {
                    JOptionPane.showMessageDialog(null, "⚠️ Student limit reached.");
                    break;
                }

                String name = JOptionPane.showInputDialog("Enter student name " + (i + 1) + ":");
                if (name == null || name.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "❌ Name cannot be empty!");
                    i--;
                    continue;
                }

                name = name.trim();

                if (isStudentAlreadyExists(name)) {
                    JOptionPane.showMessageDialog(null, "⚠️ \"" + name + "\" is already registered. Skipping...");
                    i--; // stay in loop for valid unique entry
                } else {
                    CourseDatabase.students[CourseDatabase.studentCount++] = name;
                }
            }
        } else {
            String name = JOptionPane.showInputDialog("Enter your name to register:");
            if (name == null || name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "❌ Name cannot be empty.");
                return;
            }

            name = name.trim();

            if (isStudentAlreadyExists(name)) {
                loggedInStudent = name;
                JOptionPane.showMessageDialog(null,
                        "✅ You are already registered as \"" + name + "\".\nYou can now enroll in courses.",
                        "Already Registered",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (CourseDatabase.studentCount < CourseDatabase.students.length) {
                CourseDatabase.students[CourseDatabase.studentCount++] = name;
                loggedInStudent = name;
                JOptionPane.showMessageDialog(null, "✅ Welcome, " + name + "! You are registered.");
            } else {
                JOptionPane.showMessageDialog(null, "⚠️ Student limit reached.");
            }
        }
    }


    private boolean isStudentAlreadyExists(String name) {
        if (name == null) return false;
        name = name.trim().toLowerCase();
        
        for (int i = 0; i < CourseDatabase.studentCount; i++) {
            String student = CourseDatabase.students[i];
            if (student != null && student.trim().toLowerCase().equals(name)) {
                return true;
            }
        }
        return false;
    }





    public void displayCourses() {
        if (CourseDatabase.courseCount == 0) {
            JOptionPane.showMessageDialog(null, "No courses available.");
        } else {
            StringBuilder sb = new StringBuilder("📘 Available Courses:\n");
            for (int i = 0; i < CourseDatabase.courseCount; i++) {
                sb.append((i + 1)).append(". ").append(CourseDatabase.courses[i]).append("\n");
            }
            JTextArea area = new JTextArea(sb.toString());
            area.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            area.setBackground(new Color(245, 250, 255));
            area.setForeground(new Color(30, 30, 60));
            area.setEditable(false);
            area.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 200, 240), 2, true),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            JOptionPane.showMessageDialog(null, new JScrollPane(area), "Courses", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void EnrollCourse() {
        if (loggedInStudent == null) {
            JOptionPane.showMessageDialog(null, "You must register before enrolling.");
            return;
        }

        if (CourseDatabase.courseCount == 0) {
            JOptionPane.showMessageDialog(null, "No courses available.");
            return;
        }

        StringBuilder courseList = new StringBuilder("📘 Available Courses:\n");
        for (int i = 0; i < CourseDatabase.courseCount; i++) {
            courseList.append((i + 1)).append(". ").append(CourseDatabase.courses[i]).append("\n");
        }

        String input = JOptionPane.showInputDialog(null, courseList + "\nEnter course number to enroll:");
        if (input == null) return;

        int index;
        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input.");
            return;
        }

        if (index < 1 || index > CourseDatabase.courseCount) {
            JOptionPane.showMessageDialog(null, "Invalid course number.");
            return;
        }

        String selectedCourse = CourseDatabase.courses[index - 1];
        CourseDatabase.addEnrollment(loggedInStudent, selectedCourse);
        JOptionPane.showMessageDialog(null, "✅ " + loggedInStudent + " enrolled in " + selectedCourse + " successfully.");
    }
}
