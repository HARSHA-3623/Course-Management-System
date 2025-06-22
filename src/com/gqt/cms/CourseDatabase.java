// File: com/gqt/cms/CourseDatabase.java
package com.gqt.cms;

public class CourseDatabase {
    // === Course Data ===
    public static String[] courses = new String[100];
    public static int courseCount = 0;

    // === Enrollment Data ===
    public static String[] enrolledStudentNames = new String[100];
    public static String[] enrolledCourseNames = new String[100];
    public static int enrollmentCount = 0;

    // === Student Data ===
    public static String[] students = new String[100];
    public static int studentCount = 0;

    // === Professor Data ===
    public static String[] professors = new String[100];
    public static String[] assignedCourses = new String[100]; // index aligned with professors[]
    public static int professorCount = 0;

    // === Add new enrollment ===
    public static void addEnrollment(String student, String course) {
        if (enrollmentCount < 100) {
            enrolledStudentNames[enrollmentCount] = student;
            enrolledCourseNames[enrollmentCount] = course;
            enrollmentCount++;
        } else {
            System.out.println("Enrollment limit reached!"); // fallback for debugging
        }
    }

    // === Display all enrollments ===
    public static String getEnrollmentDetails() {
        if (enrollmentCount == 0) {
            return "No enrollments available.";
        }

        StringBuilder sb = new StringBuilder("Enrollment Details:\n");
        for (int i = 0; i < enrollmentCount; i++) {
            sb.append((i + 1)).append(". ")
              .append(enrolledStudentNames[i]).append(" → ")
              .append(enrolledCourseNames[i]).append("\n");
        }
        return sb.toString();
    }

    // === Display all students ===
    public static String getStudentList() {
        if (studentCount == 0) {
            return "No students registered yet.";
        }

        StringBuilder sb = new StringBuilder("Registered Students:\n");
        for (int i = 0; i < studentCount; i++) {
            sb.append((i + 1)).append(". ").append(students[i]).append("\n");
        }
        return sb.toString();
    }

    // === Display all courses ===
    public static String getCourseList() {
        if (courseCount == 0) {
            return "No courses available.";
        }

        StringBuilder sb = new StringBuilder("Available Courses:\n");
        for (int i = 0; i < courseCount; i++) {
            sb.append((i + 1)).append(". ").append(courses[i]).append("\n");
        }
        return sb.toString();
    }

    // === Display all professors ===
    public static String getProfessorList() {
        if (professorCount == 0) {
            return "No professors registered yet.";
        }

        StringBuilder sb = new StringBuilder("Registered Professors:\n");
        for (int i = 0; i < professorCount; i++) {
            sb.append((i + 1)).append(". ").append(professors[i]).append("\n");
        }
        return sb.toString();
    }
}
