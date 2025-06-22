// File: com/gqt/cms/datamanagement/ProfessorData.java
package com.gqt.cms.datamanagement;

import com.gqt.cms.CourseDatabase;
import javax.swing.*;

public class ProfessorData {

    public void addProfessors(boolean isAdmin) {
        if (isAdmin) {
            String input = JOptionPane.showInputDialog("How many professors do you want to add?");
            if (input == null) return;

            int num;
            try {
                num = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid number.");
                return;
            }

            for (int i = 0; i < num; i++) {
                if (CourseDatabase.professorCount >= CourseDatabase.professors.length) {
                    JOptionPane.showMessageDialog(null, "Professor limit reached.");
                    break;
                }

                String name = JOptionPane.showInputDialog("Enter professor name:");
                if (name == null || name.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name cannot be empty.");
                    i--; continue;
                }

                // Check duplicate
                boolean exists = false;
                for (int j = 0; j < CourseDatabase.professorCount; j++) {
                    if (CourseDatabase.professors[j].equalsIgnoreCase(name.trim())) {
                        JOptionPane.showMessageDialog(null, "Professor '" + name + "' already exists.");
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    CourseDatabase.professors[CourseDatabase.professorCount] = name.trim();
                    CourseDatabase.assignedCourses[CourseDatabase.professorCount] = null;
                    CourseDatabase.professorCount++;
                }
            }

            JOptionPane.showMessageDialog(null, "Professors added successfully.");
        } else {
            // Self-registration
            if (CourseDatabase.professorCount >= CourseDatabase.professors.length) {
                JOptionPane.showMessageDialog(null, "Professor limit reached.");
                return;
            }

            String name = JOptionPane.showInputDialog("Enter your name:");
            if (name == null || name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Name cannot be empty.");
                return;
            }

            // Check if already registered
            for (int i = 0; i < CourseDatabase.professorCount; i++) {
                if (CourseDatabase.professors[i].equalsIgnoreCase(name.trim())) {
                    JOptionPane.showMessageDialog(null, "You are already registered.");
                    return;
                }
            }

            CourseDatabase.professors[CourseDatabase.professorCount] = name.trim();
            CourseDatabase.assignedCourses[CourseDatabase.professorCount] = null;
            CourseDatabase.professorCount++;

            JOptionPane.showMessageDialog(null, "Professor registered successfully.");
        }
    }

    public void displayProfessors() {
        if (CourseDatabase.professorCount == 0) {
            JOptionPane.showMessageDialog(null, "No professors registered.");
            return;
        }

        StringBuilder sb = new StringBuilder("Registered Professors:\n");
        for (int i = 0; i < CourseDatabase.professorCount; i++) {
            sb.append((i + 1)).append(". ").append(CourseDatabase.professors[i]).append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public int getProfessorIndex(String name) {
        for (int i = 0; i < CourseDatabase.professorCount; i++) {
            if (CourseDatabase.professors[i].equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    public void assignCourseToProfessor(String profName) {
        int index = getProfessorIndex(profName);
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "You are not registered as a professor.");
            return;
        }

        if (CourseDatabase.courseCount == 0) {
            JOptionPane.showMessageDialog(null, "No courses available to assign.");
            return;
        }

        StringBuilder courseList = new StringBuilder("Available Courses:\n");
        for (int i = 0; i < CourseDatabase.courseCount; i++) {
            courseList.append((i + 1)).append(". ").append(CourseDatabase.courses[i]).append("\n");
        }

        String course = JOptionPane.showInputDialog(null, courseList + "\nEnter course name to assign:");
        if (course == null || course.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Course name cannot be empty.");
            return;
        }

        boolean valid = false;
        for (int i = 0; i < CourseDatabase.courseCount; i++) {
            if (CourseDatabase.courses[i].equalsIgnoreCase(course.trim())) {
                valid = true;
                break;
            }
        }

        if (valid) {
            CourseDatabase.assignedCourses[index] = course.trim();
            JOptionPane.showMessageDialog(null, "Course '" + course.trim() + "' assigned to " + profName + ".");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid course name.");
        }
    }

    public void viewStudentsByCourse(String profName) {
        int index = getProfessorIndex(profName);
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "You are not registered as a professor.");
            return;
        }

        String course = CourseDatabase.assignedCourses[index];
        if (course == null) {
            JOptionPane.showMessageDialog(null, "No course assigned to you yet.");
            return;
        }

        StringBuilder sb = new StringBuilder("Students enrolled in your course (" + course + "):\n");
        boolean found = false;

        for (int i = 0; i < CourseDatabase.enrollmentCount; i++) {
            if (CourseDatabase.enrolledCourseNames[i].equalsIgnoreCase(course)) {
                sb.append("- ").append(CourseDatabase.enrolledStudentNames[i]).append("\n");
                found = true;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "No students enrolled in your course yet.");
        } else {
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }
}
