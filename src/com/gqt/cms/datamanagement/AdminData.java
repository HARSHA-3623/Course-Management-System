// File: com/gqt/cms/datamanagement/AdminData.java
package com.gqt.cms.datamanagement;

import com.gqt.cms.CourseDatabase;
import javax.swing.*;

public class AdminData {

    public void addCourse() {
        String input = JOptionPane.showInputDialog(null, "How many courses do you want to add?");
        if (input == null) return; // Cancelled

        int num;
        try {
            num = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number!");
            return;
        }

        for (int i = 0; i < num; i++) {
            if (CourseDatabase.courseCount < CourseDatabase.courses.length) {
                String course = JOptionPane.showInputDialog(null, "Enter course name:");
                if (course != null && !course.trim().isEmpty()) {
                    CourseDatabase.courses[CourseDatabase.courseCount++] = course.trim();
                } else {
                    JOptionPane.showMessageDialog(null, "Course name cannot be empty!");
                    i--; // retry this iteration
                }
            } else {
                JOptionPane.showMessageDialog(null, "Course limit reached.");
                break;
            }
        }

        JOptionPane.showMessageDialog(null, "Courses added successfully.");
    }

    public void displayCourses() {
        if (CourseDatabase.courseCount == 0) {
            JOptionPane.showMessageDialog(null, "No courses available.");
            return;
        }

        StringBuilder sb = new StringBuilder("Available Courses:\n");
        for (int i = 0; i < CourseDatabase.courseCount; i++) {
            sb.append((i + 1)).append(". ").append(CourseDatabase.courses[i]).append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void viewEnrollments() {
        if (CourseDatabase.enrollmentCount == 0) {
            JOptionPane.showMessageDialog(null, "No enrollments yet.");
            return;
        }

        StringBuilder sb = new StringBuilder("Enrollment Details:\n");
        for (int i = 0; i < CourseDatabase.enrollmentCount; i++) {
            sb.append((i + 1)).append(". ")
              .append(CourseDatabase.enrolledStudentNames[i])
              .append(" → ")
              .append(CourseDatabase.enrolledCourseNames[i])
              .append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
